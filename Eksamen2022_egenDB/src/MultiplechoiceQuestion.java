public class MultiplechoiceQuestion extends Questions {

    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    public MultiplechoiceQuestion(int id, String question, int categoryId, String correctAnswer) {
        super(id, question, categoryId, correctAnswer);
    }

    public MultiplechoiceQuestion(int id, String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer, int categoryId) {
        super(id, question, categoryId, correctAnswer);
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }

    @Override
    public String toString() {
        return "MultiplechoiceQuestion{" +
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
