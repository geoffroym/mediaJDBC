package dto;

public class MultipleChoiceQuestion extends Question {

    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

    public MultipleChoiceQuestion(int id, String questionText, String correctAnswer, String topic) {
        super(id, questionText, correctAnswer, topic);
    }

    public MultipleChoiceQuestion(int id, String questionText, String correctAnswer, String topic, String answerA, String answerB, String answerC, String answerD) {
        super(id, questionText, correctAnswer, topic);
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }

    @Override
    public void showQuestion() {
        System.out.println(getQuestionText());
        System.out.println("Please, select your answer:");
        System.out.printf("A:%s%n", answerA);
        System.out.printf("B:%s%n", answerB);
        System.out.printf("C:%s%n", answerC);
        System.out.printf("D:%s%n", answerD);
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

    @Override
    public String toString() {
        return "dto.MultipleChoiceQuestion{" +
                "answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                "} " + super.toString();
    }
}
