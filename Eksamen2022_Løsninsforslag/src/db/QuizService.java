package db;

import com.mysql.cj.jdbc.MysqlDataSource;
import dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuizService {
    private static final String REGISTER_USER_SCORE_SQL = "INSERT INTO scores (userId, score, topicId, categoryId, time) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_MC_QUESTIONS_FOR_TOPIC_SQL = "SELECT id, question, answerA, answerB, answerC, answerD, correctAnswer, topicId \n" +
            "FROM multiplechoiceQuestion WHERE topicId = (SELECT id FROM topic WHERE topic=?)";
    private static final String GET_BINARY_QUESTIONS_FOR_TOPIC_SQL = "SELECT id, question, correctAnswer, topicId \n" +
            "FROM binaryQuestion WHERE topicId = (SELECT id FROM topic WHERE topic=?)";
    private static final String GET_ALL_QUIZ_TOPICS_SQL = "SELECT topic.id, topic.topic, quizCategory.id, quizCategory.category FROM quizTopic JOIN topic ON topic.id = quizTopic.topicId JOIN quizCategory on quizCategory.id = quizTopic.categoryId";
    private static final String GET_USER_BY_NAME_SQL = "SELECT id FROM user WHERE name = ?";
    private static final String INSERT_NEW_USER_SQL = "INSERT INTO User (name) VALUES (?)";
    private static final String GET_ALL_SCORES_SQL = "SELECT scores.id, userId, name, score, topicId, topic, categoryId, category, time FROM scores JOIN user ON user.id = scores.userId JOIN topic ON topic.id = topicId JOIN quizCategory ON quizCategory.id = categoryId";
    private static final String GET_ALL_SCORES_FOR_USER_SQL =  GET_ALL_SCORES_SQL + " WHERE user.id =?";

    private MysqlDataSource quizDS;

    public QuizService() {
        quizDS = new MysqlDataSource();
        quizDS.setPassword(QuizProperties.PROPS.getProperty("pwd"));
        quizDS.setUser(QuizProperties.PROPS.getProperty("uname"));
        quizDS.setDatabaseName(QuizProperties.PROPS.getProperty("db_name"));
        quizDS.setPortNumber(Integer.parseInt(QuizProperties.PROPS.getProperty("port")));
        quizDS.setServerName(QuizProperties.PROPS.getProperty("host"));
    }

    public List<Question> getQuestions(String topic, String category) throws SQLException {
        return switch (category) {
            case "Multiple choice quiz" -> getMcQuestions(topic);
            case "Binary quiz" -> getBQuestions(topic);
            default -> throw new RuntimeException("Unknown quiz category received:" + category);
        };
    }

    private List<Question> getBQuestions(String topic) throws SQLException {
        List<Question> questions = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BINARY_QUESTIONS_FOR_TOPIC_SQL)) {
            stmt.setString(1, topic);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String question = rs.getString("question");
                    String correctAnswer = rs.getString("correctAnswer");
                    questions.add(new BinaryQuestion(id, question, correctAnswer, topic));
                }
            }
        }
        return questions;
    }

    private List<Question> getMcQuestions(String topic) throws SQLException {
        List<Question> questions = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_MC_QUESTIONS_FOR_TOPIC_SQL)) {
            stmt.setString(1, topic);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String question = rs.getString("question");
                    String answerA = rs.getString("answerA");
                    String answerB = rs.getString("answerB");
                    String answerC = rs.getString("answerC");
                    String answerD = rs.getString("answerD");
                    String correctAnswer = rs.getString("correctAnswer");
                    questions.add(new MultipleChoiceQuestion(id, question, correctAnswer, topic, answerA, answerB, answerC, answerD));
                }
            }
        }
        return questions;
    }

    public List<QuizTopic> getAllQuizTopics() throws SQLException {
        List<QuizTopic> quizTopics = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_QUIZ_TOPICS_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int topicId = rs.getInt("topic.id");
                String topic = rs.getString("topic.topic");
                int categoryId = rs.getInt("quizCategory.id");
                String category = rs.getString("quizCategory.category");
                quizTopics.add(new QuizTopic(new Topic(topicId, topic), new Category(categoryId, category)));
            }
        }
        return quizTopics;
    }

    public Optional<User> loginUser (String name) throws SQLException{
        Optional<User> user = getUser(name);
        if(user.isPresent()){
            return user;
        }
        return createNewUser(name);
    }

    private Optional<User> createNewUser(String name) throws SQLException {
        try (Connection conn = quizDS.getConnection(); //when there is an auto_incremented id, we have to pass not only sql code, but also connection with generated keys
        PreparedStatement ps = conn.prepareStatement(INSERT_NEW_USER_SQL, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, name);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected==1){
                try(ResultSet keys = ps.getGeneratedKeys()){
                    if (keys.next()){
                        return Optional.of(new User(keys.getInt(1), name));
                    }
                }
            }
        }
        return Optional.empty();
    }

    public Optional<User> getUser(String name) throws SQLException {
        try (Connection conn = quizDS.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_USER_BY_NAME_SQL)
        ){
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    int id = rs.getInt("id");
                    return Optional.of(new User(id, name));
                }
            }
        }
        return Optional.empty();
    }

    public void registerResult(Score score) throws SQLException {
        try (Connection conn = quizDS.getConnection(); //when there is an auto_incremented id, we have to pass not only sql code, but also connection with generated keys
             PreparedStatement ps = conn.prepareStatement(REGISTER_USER_SCORE_SQL)){
            ps.setInt(1, score.user().id());
            ps.setInt(2, score.score());
            ps.setInt(3, score.topic().id());
            ps.setInt(4, score.category().id());
            ps.setTimestamp(5, score.time());
            ps.executeUpdate();
        }
    }

    public List<Score> getAllScores() throws SQLException {
        List<Score> scores = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_SCORES_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int scoresId = rs.getInt("scores.id");
                int userId = rs.getInt("userId");
                String uname = rs.getString("name");
                int score = rs.getInt("score");
                int topicId = rs.getInt("topicId");
                String topic = rs.getString("topic");
                int categoryId = rs.getInt("categoryId");
                String category = rs.getString("category");
                Timestamp time = rs.getTimestamp("time");
                scores.add(new Score(scoresId, new User(userId, uname), score, new Topic(topicId, topic), new Category(categoryId, category), time));
            }
        }
        return scores;
    }

    public List<Score> getAllScoresForeUser(int userId) throws SQLException {
        List<Score> scores = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_SCORES_FOR_USER_SQL)) {
            stmt.setInt(1, userId);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int scoresId = rs.getInt("scores.id");
                    String uname = rs.getString("name");
                    int score = rs.getInt("score");
                    int topicId = rs.getInt("topicId");
                    String topic = rs.getString("topic");
                    int categoryId = rs.getInt("categoryId");
                    String category = rs.getString("category");
                    Timestamp time = rs.getTimestamp("time");
                    scores.add(new Score(scoresId, new User(userId, uname), score, new Topic(topicId, topic), new Category(categoryId, category), time));
                }
            }
        }
        return scores;
    }


}
