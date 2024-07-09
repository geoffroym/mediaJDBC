import java.util.Objects;

public abstract class Questions {
    int id;
    String question;
    int topicId;
    String correctAnswer;

    public Questions(int id, String question, int topicId, String correctAnswer) {
        this.id = id;
        this.question = question;
        this.topicId = topicId;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questions questions = (Questions) o;
        return id == questions.id && topicId == questions.topicId && correctAnswer == questions.correctAnswer && Objects.equals(question, questions.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, topicId, correctAnswer);
    }

    @Override
    public String toString() {
        return "Questions{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", topicId=" + topicId +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
