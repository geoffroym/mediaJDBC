import java.util.List;

public class User {
    private int id;
    private String name;
    private Scores scores;
    private QuizCategory categories;

    public User(int id, String name, Scores scores, QuizCategory categories) {
        this.id = id;
        this.name = name;
        this.scores = scores;
        this.categories = categories;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scores getScores() {
        return scores;
    }

    public void setScores(Scores scores) {
        this.scores = scores;
    }

    public QuizCategory getCategories() {
        return categories;
    }

    public void setCategories(QuizCategory categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                ", categories=" + categories +
                '}';
    }
}
