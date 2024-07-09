import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuizDao {
    private MysqlDataSource quizDB;

    public QuizDao() {
        quizDB = new MysqlDataSource();
        quizDB.setPassword(Quizproperties.PROPS.getProperty("pwd"));
        quizDB.setUser(Quizproperties.PROPS.getProperty("uname"));
        quizDB.setDatabaseName(Quizproperties.PROPS.getProperty("db_name"));
        quizDB.setPortNumber(Integer.parseInt(Quizproperties.PROPS.getProperty("port")));
        quizDB.setServerName(Quizproperties.PROPS.getProperty("host"));
    }

    private static final String GET_USER_SQL = "SELECT id, name FROM User WHERE name = ?";
    private static final String ADD_USER_SQL = "INSERT INTO User (name) VALUES (?)";
    private static final String GET_BINARY_QUESTIONS_SQL = "SELECT id, question, topicId, correctAnswer FROM binaryquestion";
    private static final String GET_MULTIPLECHOICE_QUESTIONS_SQL = "SELECT * FROM multiplechoiceQuestion";
    private static final String ADD_SCORE_SQL = "insert into scores (userId, score, topicId, categoryId) values (?, ?, ?, ?);";
    private static final String GET_ALL_SCORES_SQL = "SELECT id, userId, score, topicId, categoryId FROM scores";
    private static final String GET_SCORES_BY_NAME_SQL = "SELECT u.name, s.score, s.categoryId, c.category \n" +
            "FROM User u JOIN scores s ON u.id = s.userId\n" +
            "JOIN quizcategory c ON s.categoryId = c.id WHERE u.name = ?";
    private static final String GET_ALL_USERS_SQL = "SELECT id, name FROM user";
    private static final String GET_CATEGORY_SQL = "SELECT id, category FROM quizcategory";

    public List<User> getUser() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = quizDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_USERS_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                users.add(new User(id, name));
            }
        }
        return users;
    }

    public Optional<User> getUserByName(String name) throws SQLException {
        try (Connection conn = quizDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_USER_SQL)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    return Optional.of(new User(id, name));
                }
            }
        }
        return Optional.empty();
    }

    public void addNewUser(User user) throws SQLException {
        try (Connection conn = quizDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_USER_SQL)
        ) {
            stmt.setString(1, user.getName());
            stmt.executeUpdate();
        }
    }

    public List<QuizCategory> getQuizCategory() throws SQLException {
        List<QuizCategory> quizCategories = new ArrayList<>();
        try (Connection conn = quizDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_CATEGORY_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String category = rs.getString("category");
                quizCategories.add(new QuizCategory(id, category));
            }
        }
        return quizCategories;
    }

    public List<BinaryQuestion> getBinaryQuestions() throws SQLException {
        List<BinaryQuestion> binaryQuestions = new ArrayList<>();
        try (Connection conn = quizDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BINARY_QUESTIONS_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                int topicId = rs.getInt("topicId");
                String correctAnswer = rs.getString("correctAnswer");
                QuizCategory quizCategory = null;
                QuizTopic quizTopic = new QuizTopic(topicId, quizCategory.id());
                binaryQuestions.add(new BinaryQuestion(id, question, topicId, correctAnswer));
            }
        }
        return binaryQuestions;
    }

    public List<MultipleChoiceQuestion> getMultipleChoiceQuestions() throws SQLException {
        List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();
        try (Connection conn = quizDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_MULTIPLECHOICE_QUESTIONS_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                String answerA = rs.getString("answerA");
                String answerB = rs.getString("answerB");
                String answerC = rs.getString("answerC");
                String answerD = rs.getString("answerD");
                String correctAnswer = rs.getString("correctAnswer");
                int topicId = rs.getInt("topicId");
                multipleChoiceQuestions.add(new MultipleChoiceQuestion(id, question, topicId, correctAnswer, answerA, answerB, answerC, answerD));
            }
        }
        return multipleChoiceQuestions;
    }

    public void addNewScore(int userId, int score, int topicId, int categoryId) throws SQLException {
        try (Connection conn = quizDB
                .getConnection()) {
            PreparedStatement ps = conn.prepareStatement(ADD_SCORE_SQL);
            ps.setInt(1, userId);
            ps.setInt(2, score);
            ps.setInt(3, topicId);
            ps.setInt(4, categoryId);
            ps.executeUpdate();
        }
    }

    public List<Scores> getAllScores() throws Exception {
        //int id, int userId, int score, int topicId, int categoryId, LocalTime time
        List<Scores> scores = new ArrayList<>();
        try (Connection conn = quizDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SCORES_SQL)
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userId");
                int score = rs.getInt("score");
                int topicId = rs.getInt("topicId");
                int categoryId = rs.getInt("topicId");
                //LocalTime time = rs.getTime("time").toLocalTime();
                scores.add(new Scores(id, userId, score, topicId, categoryId));
            }
        }
        return scores;
    }

    public List<Scores> getScoresByName(String name) throws SQLException {
        List<Scores> scores = new ArrayList<>();
        //u.name, s.score, s.categoryId, c.category
        try (Connection conn = quizDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_SCORES_BY_NAME_SQL)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int score = rs.getInt("s.score");
                    int categoryId = rs.getInt("s.categoryId");
                    String categoryName = rs.getString("c.category");
                    User user = new User(name);
                    QuizCategory categories = new QuizCategory(categoryId, categoryName);
                    scores.add(new Scores(score, categories, user));
                }
            }
        }
        return scores;
    }
}

