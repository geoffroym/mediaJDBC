public class Scores {
    private int id;
    private int userId;
    private int score;
    private int categoryId;
    private User user;

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Scores(int id, int userId, int score, int categoryId, User user) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.categoryId = categoryId;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "id=" + id +
                ", userId=" + userId +
                ", score=" + score +
                ", categoryId=" + categoryId +
                ", user=" + user +
                '}';
    }
}
