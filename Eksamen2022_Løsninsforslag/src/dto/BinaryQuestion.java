package dto;

public class BinaryQuestion extends Question {

    public BinaryQuestion(int id, String questionText, String correctAnswer, String topic) {
        super(id, questionText, correctAnswer, topic);
    }

    @Override
    public void showQuestion() {
        System.out.println("Is this statement true(T) or false(F)?");
        System.out.println(getQuestionText());
    }

    @Override
    public String toString(){ return "dto.BinaryQuestion{} " + super.toString();}
}
