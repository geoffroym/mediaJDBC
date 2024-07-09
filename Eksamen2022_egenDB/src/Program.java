import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    private User loggedInUser = null;
    private final QuizData quizData;

    public Program(){
        quizData = new QuizData();
    }

    public void mainMenu() throws Exception {
        System.out.println("WELCOME");
        String choice = "";
        while (!choice.equals("q")){
            displayMainMenu();
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();
            switch (choice){
                case "s" -> signIn();
                case "c" -> chooseCategory();
                case "o" -> options();
                case "q" -> quit();
                default -> System.out.println("please provide valid input.");
            }
        }
    }

    private void quit() {
        System.out.println("Bye and welcome back!");
    }

    private void options() throws Exception {
        System.out.println("a. List of all scores");
        System.out.println("b. List of your scores");
        String answer = "";
        Scanner scanner = new Scanner(System.in);
        while (!(answer.equalsIgnoreCase("a") || (answer.equalsIgnoreCase("b") || (answer.equalsIgnoreCase("c"))))){
            answer = scanner.nextLine();
            switch (answer){
                case "a" -> seeAllScores();
                case "b" -> userScores();
                case "c" -> mainMenu();
            }
        }
    }

    private void userScores() throws Exception {
        System.out.println("Enter username:");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();

        List<Scores> scores = quizData.getScoresByUser(userName);

        if (scores.isEmpty()) {
            System.out.println("No scores found for user: " + userName);
        } else {
            System.out.println("List of scores for user: " + userName);
            for (Scores score : scores) {
                System.out.println("Score: " + score.getScore() + ", Category ID: " + score.getCategoryId());
            }
        }
        System.out.println("Press any key to return to main menu.");
        scanner.nextLine();
        mainMenu();
    }

    private void seeAllScores() throws Exception {
        List<Scores> scores = quizData.getAllScores();
        for (Scores score : scores) {
            System.out.println("User: " + score.getUser().getName() + ", Score: " + score.getScore());
        }
    }

    private void chooseCategory() throws Exception {
        System.out.println("Here are your options:");
        System.out.println("a. Multiplechoice quiz(Football)");
        System.out.println("b. Binary quiz (Tennis)");
        String choice = "";
        Scanner scanner = new Scanner(System.in);
        while (!(choice.equalsIgnoreCase("a") || (choice.equalsIgnoreCase("b")) || (choice.equalsIgnoreCase("c")))){
            choice = scanner.nextLine();
            switch (choice){
                case "a" -> multiplechoiceQuiz();
                case "b" -> binaryQuiz();
                case "c" -> mainMenu();
                default -> System.out.println("Choose 'a' or 'b'");
            }
        }
    }

    private void binaryQuiz() throws Exception {
        List<BinaryQuestion> binaryQuestions = this.quizData.getBinaryQuestions();
        Scanner scanner = new Scanner(System.in);
        int counter = 0;

        System.out.println("Choose an answer from T(true) or F(false):");
        for (BinaryQuestion question: binaryQuestions){
            System.out.println(question.getQuestion());
            String answer = scanner.nextLine();
            if(answer.equalsIgnoreCase((question.getCorrectAnswer()))){
                System.out.println("You answered " + answer + "\nYour answer is correct!");
                counter++;
                System.out.println("Score: " + counter);
            } else {
                System.out.println("Wrong answer!");
            }
        }
        System.out.println("Final score: " + counter);
        quizData.addNewScore(loggedInUser.getId(), counter, 2);
        System.out.println("Press any key to return to main menu.");
        scanner.nextLine();
        mainMenu();
    }

    private void multiplechoiceQuiz() throws Exception {
        List<MultiplechoiceQuestion> multiplechoiceQuestions = quizData.getMultiplechoiceQuestions();
        Scanner scanner = new Scanner(System.in);
        int counter = 0;

        System.out.println("Choose an answer from A - D:");
        for (MultiplechoiceQuestion question : multiplechoiceQuestions) {
            System.out.println("Question: " + question.getQuestion());
            System.out.println("A: " + question.getAnswerA());
            System.out.println("B: " + question.getAnswerB());
            System.out.println("C: " + question.getAnswerC());
            System.out.println("D: " + question.getAnswerD());
            String answer = scanner.nextLine();
            if(answer.equalsIgnoreCase(question.getCorrectAnswer())){
                System.out.println("You answered " + answer + "\nYour answer is correct!");
                counter++;
                System.out.println("Score: " + counter);
            } else {
                System.out.println("Wrong answer!");
            }
        }
        System.out.println("Final score: " + counter);
        quizData.addNewScore(loggedInUser.getId(), counter, 1);
        System.out.println("Press any key to return to main menu.");
        scanner.nextLine();
        mainMenu();
    }

    private void signIn() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user name:");
        String userName = scanner.nextLine();
        Optional<User> possibleUser = quizData.getUserByName(userName);
        if (possibleUser.isEmpty()){
            System.out.println("User not found. Please enter new user name:");
            String newUserName = scanner.nextLine();
            User newUser = new User(newUserName);
            quizData.addNewUser(newUser);
            loggedInUser = newUser;
        } else {
            System.out.println("Welcome back, " + userName);
            loggedInUser = possibleUser.get();
        }
    }

    private void displayMainMenu() {
        if (loggedInUser == null){
            System.out.println("s. Sign in");
        }
        if (loggedInUser != null){
            System.out.println("c. See quiz categories");
            System.out.println("o. Options");
        }
        System.out.println("q. Quit");
    }
}
