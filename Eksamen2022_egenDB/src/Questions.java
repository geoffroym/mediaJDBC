public abstract class Questions {
    private int id;
    private String question;
    private int categoryId;
    private String correctAnswer;

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Questions(int id, String question, int categoryId, String correctAnswer) {
        this.id = id;
        this.question = question;
        this.categoryId = categoryId;
        this.correctAnswer = correctAnswer;
    }
}
