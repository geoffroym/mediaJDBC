import java.sql.SQLException;
import java.util.*;

public class Program {
    private User loggedInUser = null;
    private final QuizDao quizDao;
    private int counter = 0;

    public Program() {
        quizDao = new QuizDao();
    }

    public void mainMenu() throws Exception {
        System.out.println("Welcome to our Quiz.");
        String choice = "";
        while (!choice.equals("q")) {
            displayMainMenu();
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();
            switch (choice) {
                case "s" -> signInOut();
                case "t" -> chooseQuizTopic();
                case "o" -> options();
                case "q" -> quit();
                default -> System.out.println("Please provide a valid input.");
            }
        }
    }

    private void quit() {
        System.out.println("Bye and welcome back!");
    }

    private void options() throws Exception {
        System.out.println("You have these options:");
        System.out.println("a. List of all scores for a user.");
        System.out.println("b. List of all scores.");
        System.out.println("c. List of top 3 scores.");
        System.out.println("d. Go to main menu.");
        Scanner scanner = new Scanner(System.in);
        String answer = "";
        while (!(answer.equals("a") || answer.equals("b") || answer.equals("c") || answer.equals("d"))){
            answer = scanner.nextLine();
            switch (answer){
                case "a" -> showListOfScoresByUser();
                case "b" -> showListOfUserScores();
                case "c" -> showTopThree();
                case "d" -> mainMenu();
                default -> System.out.println("Please provide an answer (a - d)");
            }

        }
    }

    private void showTopThree() {
        /*StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Here it is");
        List<Integer> registeredStudentIds = eventDao.getRegisteredStudentIds();
        List<StudyProgram> studyPrograms = universityDao.getStudyProgram();
        for (StudyProgram studyProgram : studyPrograms) {
            stringBuilder.append(studyProgram.name()).append(" - ").append(studyProgram.programResponsible().getName());
            stringBuilder.append(" - 1 minute+ \n");
            if(studentRegistered && student.getStudyProgram().id() == studyProgram.id()){
                stringBuilder.append("== You are registered as part of this program == \n");
            }
            int registeredStudentsInProgram = getNumberOfRegisteredStudentsInProgram(studyProgram.id(), registeredStudentIds);
            stringBuilder.append("\t").append(registeredStudentsInProgram).append(" student(s) - ");
            stringBuilder.append((1+registeredStudentsInProgram/5)).append(" minutes\n");
            stringBuilder.append("Break - 5 minutes\n");
        }
        stringBuilder.append("Closing remarks: 15 minutes\n");
        return  stringBuilder.toString();*/
    }

    private void showListOfUserScores() throws Exception {
        List<Scores> scores = quizDao.getAllScores();
        List<User> users = quizDao.getUser();
        for (Scores s: scores){
            System.out.print(s);
        }
    }

    private void showListOfScoresByUser() throws SQLException {
        System.out.println("Enter user:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println(quizDao.getScoresByName(name));
    }

    private void chooseQuizTopic() throws Exception {
        System.out.println("Choose Quiz topic:");
        System.out.println("A. Football (Multiplechoice Quiz)");
        System.out.println("B. Tennis (Binary Quiz)");
        String choice = "";
        Scanner scanner = new Scanner(System.in);
        while (!(choice.toUpperCase().equals("a")) || !(choice.toUpperCase().equals("b"))) {
            choice = scanner.nextLine();
            switch (choice) {
                case "a" -> multiplechoiceQuiz();
                case "b" -> binaryQuiz();
                default -> System.out.println("Please, choose 'a' or 'b'");
            }
        }
    }

    private void binaryQuiz() throws Exception {
        List<BinaryQuestion> binaryQuestions = quizDao.getBinaryQuestions();

        Random random = new Random();
        int rand = random.nextInt(binaryQuestions.size());

        System.out.println("Choose an answer from T(true) or F(false):");
        while (binaryQuestions.size() > 0) {
            System.out.println(binaryQuestions.get(rand).getQuestion());
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase(binaryQuestions.get(rand).getCorrectAnswer())) {
                System.out.println("You answered " + answer + "\nYour answer is correct!");
                counter++;
                System.out.println("Score: " + counter);
                quizDao.addNewScore(loggedInUser.getId(), counter, 2, 2);
            } else {
                System.out.println("Wrong answer!");
            }
            binaryQuestions.remove(rand);
            System.out.println("Would you like to:");
            System.out.println("a. Continue playing.");
            System.out.println("b. exit current round.");
            String choice = "";
            while (!(choice.toUpperCase().equals("a")) || !(choice.toUpperCase().equals("b"))) {
                choice = scanner.nextLine();
                switch (choice) {
                    case "a" -> binaryQuiz();
                    case "b" -> mainMenu();
                    default -> System.out.println("Please, choose 'a' or 'b'");
                }
            }
        }
    }

    private void multiplechoiceQuiz() throws Exception {
        List<MultipleChoiceQuestion> multipleChoiceQuestions = quizDao.getMultipleChoiceQuestions();

        Random random = new Random();
        int rand = random.nextInt(multipleChoiceQuestions.size());

        System.out.println("Choose an answer from a - d:");
        while (multipleChoiceQuestions.size() > 0) {
            System.out.println(multipleChoiceQuestions.get(rand).getQuestion());
            System.out.println("a. " + multipleChoiceQuestions.get(rand).getAnswerA());
            System.out.println("b. " + multipleChoiceQuestions.get(rand).getAnswerB());
            System.out.println("c. " + multipleChoiceQuestions.get(rand).getAnswerC());
            System.out.println("d. " + multipleChoiceQuestions.get(rand).getAnswerD());
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase(multipleChoiceQuestions.get(rand).getCorrectAnswer())) {
                System.out.println("You answered " + answer + "\nYour answer is correct!");
                counter++;
                System.out.println("Score: " + counter);
                quizDao.addNewScore(loggedInUser.getId(), counter, 1, 1);
            } else {
                System.out.println("Wrong answer!");
            }
            multipleChoiceQuestions.remove(rand);
            System.out.println("Would you like to:");
            System.out.println("a. Continue playing.");
            System.out.println("b. exit current round.");
            String choice = "";
            while (!(choice.toUpperCase().equals("a")) || !(choice.toUpperCase().equals("b"))) {
                choice = scanner.nextLine();
                switch (choice) {
                    case "a" -> multiplechoiceQuiz();
                    case "b" -> mainMenu();
                    default -> System.out.println("Please, choose 'a' or 'b'");
                }
            }
        }
    }

    private void displayMainMenu() {
        if (loggedInUser == null) {
            System.out.println("'s' to sign in");
        }
        if (loggedInUser != null) {
            System.out.println("'t' for quiz topic");
            System.out.println("'o' for Options");
        }
        System.out.println("'q' to quit");
    }

    private void signInOut() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user name:");
        String userName = scanner.nextLine();
        Optional<User> possibleUser = quizDao.getUserByName(userName);
        if (possibleUser.isEmpty()) {
            System.out.println("We can't find you in our system. \n" +
                    "Please, enter new user name:");
            String newUsername = scanner.nextLine();
            User newUser = new User(newUsername);
            quizDao.addNewUser(newUser);
            loggedInUser = newUser;

        } else {
            System.out.println("Welcome back, " + userName);
            loggedInUser = possibleUser.get();
        }
    }
}
