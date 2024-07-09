import db.EventService;
import dto.Guest;
import dto.Person;
import dto.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    private Student loggedInUser = null;
    private final EventService eventService;

    public Program() {
        eventService = new EventService();
    }

    public void mainMenu() throws SQLException {
        String choice = "";
        System.out.println("Welcome to event information!");
        while (!choice.equals("q")) {
            displayMainMenu();
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine().toLowerCase();
            switch (choice) {
                case "q" -> quit();
                case "1" -> loggInOut();
                case "2" -> seeProgram();
                case "3" -> searchParticipants();
                case "4" -> seeAllParticipants();
                case "5" -> seeAllStudentParticipants();
                case "6" -> SeeParticipantsForProgram();
                case "7" -> handleRegistration();
                default -> System.out.println("Please provide a valid input.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("Here are your options:");
        String signInOut = (loggedInUser == null) ? "Sign in" : "Sign out";
        //things you can see independently on if you're logged in or not
        System.out.printf("1: %s%n", signInOut); //%s takes the string the if statement above results in and shift line (%n)
        System.out.println("2: See overall program.");
        System.out.println("3: Search for participant.");
        System.out.println("4: See all participants.");
        System.out.println("5: See all student participants.");
        //things you can see only if you're logged in
        if (loggedInUser != null) {
            System.out.println("6: See student participants for your program.");
            System.out.println("7: Your registration.");

        }
        System.out.println("q: Quit.");
    }

    private void loggInOut() throws SQLException {
        if (loggedInUser != null) { //statement to allow logged-in user to logg out
            loggedInUser = null;
            System.out.println("You are logged out.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide your name:");
        String name = scanner.nextLine();
        Optional<Student> possibleStudent = eventService.getStudentByName(name);
        if (possibleStudent.isEmpty()) {
            System.out.println("Unable to login. Name not found.");
        } else {
            System.out.println("Login successful.");
            loggedInUser = possibleStudent.get();
        }
    }

    private void handleRegistration() throws SQLException {
        //boolean connected to method that returns true if there is a row when searching student id
        boolean isRegistered = eventService.isStudentRegisteredForEvent(loggedInUser);
        //System.out.println("registered:" + isRegistered);
        if (!isRegistered) {
            offerStudentRegistration();
        } else {
            offerStudentRegistrationAjustment();
        }
    }

    private void offerStudentRegistration() throws SQLException {
        System.out.println("You are currently not registered. Would you like to register? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String answer = "";
        while (!(answer.equals("y") || (answer.equals("n")))) {
            answer = scanner.nextLine();
            switch (answer) {
                case "y" -> registerStudent();
                case "n" -> System.out.println("Ok, you will remain unregistered.");
                default -> System.out.println("Please provide y or n as answer.");
            }
        }
    }

    private void registerStudent() throws SQLException {
        getGuestInputFromUser();
        eventService.addStudentRegistration(loggedInUser);
    }

    private void getGuestInputFromUser() {
        System.out.println("How many guests would you like to invite? (1-4)");
        boolean validInput = false;
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!validInput) {
            input = scanner.nextLine();
            validInput = validadeNumberOfGuestsInput(input);
        }
        int numberOfGuests = Integer.parseInt(input);
        loggedInUser.getGuests().clear();
        for (int i = 0; i < numberOfGuests; i++) {
            System.out.println("Enter name for guest:" + (i + 1));
            String name = scanner.nextLine();
            loggedInUser.getGuests().add(new Guest(name, loggedInUser));
        }
    }

    private boolean validadeNumberOfGuestsInput(String input) {
        try {
            int numberOgGuests = Integer.parseInt(input);
            return numberOgGuests >= 0 && numberOgGuests < 5;
        } catch (NumberFormatException nfe) {
            System.out.println("Please provide a valid number between 1 and 4");
        }
        return false;
    }

    private void offerStudentRegistrationAjustment() throws SQLException {
        System.out.println("You are currently registered.");
        List<Guest> guest = eventService.getGuestsForStudent(loggedInUser);
        if (guest.isEmpty()) {
            System.out.println("You currently have invited no guests.");
        } else {
            System.out.println("Here are your invited guests:");
            for (Guest guest1 : guest) {
                System.out.println(guest1);
            }
        }
        System.out.println("You have three choices:");
        System.out.println("1. Not make any changes to your current registration.");
        System.out.println("2. Change your list of guests.");
        System.out.println("3. Cancel registration.");
        Scanner scanner = new Scanner(System.in);
        String answer = "";
        while (!(answer.equals("1") || answer.equals("2") || answer.equals("3"))) {
            answer = scanner.nextLine();
            switch (answer) {
                case "1" -> System.out.println("Ok. Your registration remains the same.");
                case "2" -> adjustStudentGuests();
                case "3" -> deleteStudentRegistration();
                default -> System.out.println("Please provide a choice (1-3).");
            }
        }
    }

    private void adjustStudentGuests() throws SQLException {
        getGuestInputFromUser();
        eventService.updateStudentRegistration(loggedInUser);
    }

    private void deleteStudentRegistration() throws SQLException {
        System.out.println("Deleting registration.");
        eventService.deleteStudentRegistration(loggedInUser);
    }

    private void SeeParticipantsForProgram() throws SQLException {
        System.out.println("Here are all students registered for your program:");
        List<Student> students = eventService.getAllRegisteredStudentsInProgram(loggedInUser.getStudyProgram().id());
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void seeAllStudentParticipants() throws SQLException {
        List<Student> allRegisteredStudents = eventService.getAllRegisteredStudents();
        for (Student allRegisteredStudent : allRegisteredStudents) {
            System.out.println(allRegisteredStudent);
        }
    }

    private void seeAllParticipants() throws SQLException {
        List<Person> participants = eventService.getAllParticipants();
        System.out.println("All participants:");
        for (Person participant : participants) {
            System.out.println(participant);
        }
    }

    private void searchParticipants() throws SQLException {
        System.out.println("Who are you searching for?");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        boolean attending = eventService.isStudentRegisteredForEvent(name);
        if (attending) {
            System.out.printf("Yes, %s is attending the event%n", name);
        } else {
            System.out.printf("No, %s is not attending the event%n", name);
        }
    }

    private void seeProgram() throws SQLException {
        System.out.println("here is the program:");
        System.out.println(eventService.getCeremonyProgram(loggedInUser));
    }

    private void quit() {
        System.out.println("Bye!");
    }
}
