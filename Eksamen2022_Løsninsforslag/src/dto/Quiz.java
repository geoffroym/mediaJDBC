package dto;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    private final List<Question> questions;
    private final User user;
    private final QuizTopic quizTopic;

    public Quiz(List<Question> questions, User user, QuizTopic quizTopic) {
        this.questions = questions;
        this.user = user;
        this.quizTopic = quizTopic;
    }

    public Score runQuiz(){
        if(questions.size()<5){
            throw new RuntimeException("Unable to play quiz. Too few questions available.");
        }
        int score = 0;
        Collections.shuffle(questions);
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            Question q = questions.get(i);
            q.showQuestion();
            String answer = scanner.nextLine();
            if(q.isCorrectAnswer(answer))
                score++;
        }
        return new Score(0, user, score, quizTopic.topic(), quizTopic.category(), new Timestamp(System.currentTimeMillis()));
    }
}
