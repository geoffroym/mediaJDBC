public class BinaryQuestion extends Questions {
    public BinaryQuestion(int id, String question, int topicId, String correctAnswer) {
        super(id, question, topicId, correctAnswer);
    }

    public String toString() {
        return "BinaryQuestion{} " + super.toString();
    }
}
