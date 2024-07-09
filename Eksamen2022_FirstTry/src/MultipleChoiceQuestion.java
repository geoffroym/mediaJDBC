public class MultipleChoiceQuestion extends Questions{
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

    public MultipleChoiceQuestion(int id, String question, int topicId, String  correctAnswer) {
        super(id, question, topicId, correctAnswer);
    }

    public MultipleChoiceQuestion(int id, String question, int topicId, String correctAnswer, String answerA, String answerB, String answerC, String answerD) {
        super(id, question, topicId, correctAnswer);
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestion{" +
                "answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                "} " + super.toString();
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }
}
