import java.util.List;
import java.util.Locale;

public class Scores {
    private int id;
    private int userId;
    private int score;
    private int topicId;
    private int categoryId;
    private User user;
    private QuizCategory categories;

    public Scores(int score, QuizCategory categories,User user){
        this.score = score;
        this.categories = categories;
        this.user = user;
    }

    public Scores(int id, int userId, int score, int topicId, int categoryId) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.topicId = topicId;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "id=" + id +
                ", userId=" + userId +
                ", score=" + score +
                ", topicId=" + topicId +
                ", categoryId=" + categoryId +
                ", user=" + user +
                ", categories=" + categories +
                '}';
    }

    public QuizCategory getCategories() {
        return categories;
    }

    public void setCategories(QuizCategory categories) {
        this.categories = categories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
