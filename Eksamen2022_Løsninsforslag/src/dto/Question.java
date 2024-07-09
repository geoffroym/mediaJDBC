package dto;

public abstract class Question {
    private int id;
    private String questionText;
    private String correctAnswer;
    private String topic;

    public Question(int id, String questionText, String correctAnswer, String topic) {
        this.id = id;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public abstract void showQuestion();

    public boolean isCorrectAnswer(String text) {return text.equalsIgnoreCase(correctAnswer);}

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("dto.Question{");
        sb.append("id=").append(id);
        sb.append(", questionText='").append(questionText).append('\'');
        sb.append(", correctAnswer='").append(correctAnswer).append('\'');
        sb.append(", topic='").append(topic).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
