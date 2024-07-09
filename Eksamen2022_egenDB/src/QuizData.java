import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuizData {
    private MysqlDataSource quizDS;

    public QuizData() {
        quizDS = new MysqlDataSource();
        quizDS.setPassword(QuizProperties.PROPS.getProperty("pwd"));
        quizDS.setUser(QuizProperties.PROPS.getProperty("uname"));
        quizDS.setDatabaseName(QuizProperties.PROPS.getProperty("db_name"));
        quizDS.setPortNumber(Integer.parseInt(QuizProperties.PROPS.getProperty("port")));
        quizDS.setServerName(QuizProperties.PROPS.getProperty("host"));
    }

    private static final String ADD_USER_SQL = "INSERT INTO user (name) VALUES (?)";
    private static final String GET_USER_BY_NAME_SQL = "SELECT id, name FROM user WHERE name=?";
    //private static final String GET_CATEGORY_SQL = "SELECT * FROM quizcategory WHERE id=?";
    private static final String GET_BINARY_QUIZ_SQL = "SELECT id, question, correctAnswer, categoryId FROM binaryquestion";
    private static final String ADD_SCORE_SQL = "INSERT INTO scores (userId, score, categoryId) VALUES (?,?,?)";
    private static final String GET_MULTIPLECHOICE_QUIZ_SQL = "SELECT id, question, answerA, answerB, answerC, answerD, correctAnswer, categoryId FROM multiplechoicequestion"; ;
    private static final String GET_ALL_SCORES_SQL = "select u.name, u.id, s.id, s.score, s.categoryId\n" +
            "FROM user u JOIN scores s ON u.id = s.userId;";
    private static final String GET_SCORES_BY_USER_SQL = "SELECT s.id, s.userId, s.score, s.categoryId, u.id AS userId, u.name AS userName " +
            "FROM scores s JOIN user u ON s.userId = u.id WHERE u.name = ?";
    private static final String GET_ALL_USERS_SQL = "SELECT id, name FROM USER";

    public void addNewUser(User user) throws SQLException {
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_USER_SQL)
        ) {
            stmt.setString(1, user.getName());
            stmt.executeUpdate();
        }
    }

    public Optional<User> getUserByName(String name) throws SQLException {
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_USER_BY_NAME_SQL)
        ) {
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

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_USERS_SQL);
             ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                users.add(new User(id, name));
            }

        }
        return users;
    }

    /*public List<Integer> getCategoryIds() throws SQLException {
        List<Integer> categoryIds = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_CATEGORY_SQL);
             ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                categoryIds.add(rs.getInt("id"));
            }
        }
        return categoryIds;
    }*/

    public List<BinaryQuestion> getBinaryQuestions() throws SQLException {
        List<BinaryQuestion> binaryQuestions = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BINARY_QUIZ_SQL);
             ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                String correctAnswer = rs.getString("correctAnswer");
                int categoryId = rs.getInt("categoryId");
                binaryQuestions.add(new BinaryQuestion(id, question, categoryId, correctAnswer));
            }

        }
        return binaryQuestions;
    }

    public List<MultiplechoiceQuestion> getMultiplechoiceQuestions() throws SQLException {
        List<MultiplechoiceQuestion> multiplechoiceQuestions = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_MULTIPLECHOICE_QUIZ_SQL);
             ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                String answerA = rs.getString("answerA");
                String answerB = rs.getString("answerB");
                String answerC = rs.getString("answerC");
                String answerD = rs.getString("answerD");
                String correctAnswer = rs.getString("correctAnswer");
                int categoryId = rs.getInt("categoryId");
                multiplechoiceQuestions.add(new MultiplechoiceQuestion(id, question, answerA, answerB, answerC, answerD, correctAnswer, categoryId));
            }

        }
        return multiplechoiceQuestions;
    }

    public void addNewScore(int userId, int score, int categoryId) throws SQLException {
        try (Connection conn = quizDS
                .getConnection()) {
            PreparedStatement ps = conn.prepareStatement(ADD_SCORE_SQL);
            ps.setInt(1, userId);
            ps.setInt(2, score);
            ps.setInt(3, categoryId);
            ps.executeUpdate();
        }
    }

    //get all scores
    public List<Scores> getAllScores() throws Exception {
        // u.name, u.id, s.id, s.score, s.categoryId
        List<Scores> scores = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SCORES_SQL)
        ) {
            while (rs.next()) {
                String name = rs.getString("u.name");
                int userId = rs.getInt("u.id");
                int scoreId = rs.getInt("s.id");
                int score = rs.getInt("s.score");
                int categoryId = rs.getInt("s.categoryId");
                User user = new User(userId, name);
                scores.add(new Scores(scoreId, userId, score, categoryId, user));
            }
        }
        return scores;
    }

    public List<Scores> getScoresByUser(String userName) throws SQLException {
        List<Scores> scores = new ArrayList<>();
        try (Connection conn = quizDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_SCORES_BY_USER_SQL)) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int scoreId = rs.getInt("s.id");
                    int userId = rs.getInt("s.userId");
                    int score = rs.getInt("s.score");
                    int categoryId = rs.getInt("s.categoryId");
                    String userNameFromDB = rs.getString("userName");

                    User user = new User(userId, userNameFromDB);
                    scores.add(new Scores(scoreId, userId, score, categoryId, user));
                }
            }
        }
        return scores;
    }
}
