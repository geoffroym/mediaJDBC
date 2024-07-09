import db.QuizService;
import dto.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    private final QuizService quizService;
    private User loggedInUser;
    private final List<QuizTopic> availableQuizTopics;

    public Program() throws SQLException {
        quizService = new QuizService();
        availableQuizTopics = quizService.getAllQuizTopics();
        loginUser();
        menu();
    }

    private void menu() throws SQLException {
        String choice = "0";
        while (!(choice.equalsIgnoreCase("4"))) {
            displayMainMenu();
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> playQuiz();
                case "2" -> listUserScores();
                case "3" -> listHighScores();
                case "4" -> quit();
            }
        }
    }

    private void playQuiz() throws SQLException {
        QuizTopic chosenFormat = chooseQuizFormat();
        List<Question> questions = quizService.getQuestions(chosenFormat.topic().topic(), chosenFormat.category().category());
        Quiz quiz = new Quiz(questions, loggedInUser, chosenFormat);
        Score score = quiz.runQuiz();
        quizService.registerResult(score);
    }

    private QuizTopic chooseQuizFormat() {
        System.out.println("What quiz do you want to play?");
        while (true) {
            for (int i = 1; i < availableQuizTopics.size() + 1; i++) {
                System.out.printf("%d: %s (%s)%n", i, availableQuizTopics.get(i - 1).topic().topic(), availableQuizTopics.get(i - 1).category().category());
            }
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            try {
                int pick = Integer.parseInt(answer);
                if (pick < 1 || pick > availableQuizTopics.size()) {
                    throw new NumberFormatException();
                }
                return availableQuizTopics.get(pick - 1);
            } catch (NumberFormatException nfe) {
                System.out.println("Please try again.");
            }
        }
    }

    private void listUserScores() throws SQLException {
        List<Score> scores = quizService.getAllScoresForeUser(loggedInUser.id());
        System.out.println("All your scores:");
        scores.forEach(System.out::println);
    }

    private void listHighScores() throws SQLException {
        System.out.println("Top 10 scores:");
        List<Score> scores = quizService.getAllScores();
        scores.stream()
                .sorted(new ScoreComparator())
                .limit(10)
                .forEach(System.out::println);
    }

    private void quit() {
        System.out.println("Bye and welcome back!");
    }

    private void displayMainMenu() {
        System.out.println("Here are your options:");
        System.out.println("1. Play a quiz");
        System.out.println("2. List all your scores");
        System.out.println("3. List high scores");
        System.out.println("4. Quit");
    }

    private void loginUser() throws SQLException {
        System.out.println("Please enter your name to start playing:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Optional<User> user = quizService.loginUser(name);
        if (user.isEmpty()) {
            throw new RuntimeException("Unable to continue. User not able to login.");
        }
        this.loggedInUser = user.get();
    }


}
