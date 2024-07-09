import Comparators.BooksComparator;
import Comparators.GamesComparator;
import Comparators.MoviesComparator;
import db.MediaService;
import dto.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    private final MediaService mediaService;
    private User loggedInUser;

    public Program() throws SQLException {
        mediaService = new MediaService();
        loginUser();
        mainMenu();
    }

    private void loginUser() throws SQLException {
        System.out.println("Hi and welcome to our media library. \n" +
                "Please, enter your username to enter the library.");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        Optional<User> user = mediaService.loginUser(username);
        if (user.isEmpty()) {
            throw new RuntimeException("Something went wrong. User not able to login.");
        }
        this.loggedInUser = user.get();
    }

    public void mainMenu() throws SQLException {
        String choice = "0";
        Scanner scanner = new Scanner(System.in);
        while (!(choice.equals("5"))) {
            displayMainMenu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> optionsForBooks(scanner);
                case "2" -> optionsForMovies(scanner);
                case "3" -> optionsForGames(scanner);
                case "4" -> searchMedia();
                case "5" -> quit();
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("Hi, " + loggedInUser.username() + "\nHere are your options:");
        System.out.println("1. See options for books");
        System.out.println("2. See options for movies");
        System.out.println("3. See options for games");
        System.out.println("4. Search the library");
        System.out.println("5. Quit");
    }

    private void optionsForBooks(Scanner scanner) throws SQLException {
        String choice = "";
        while (!(choice.equalsIgnoreCase("5"))) {
            System.out.println("You chose books.");
            System.out.println("1. See all books (ordered by title)");
            System.out.println("2. See books by author");
            System.out.println("3. Add a book");
            System.out.println("4. Remove a book");
            System.out.println("5. Back to main menu");
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showOrderedBooks();
                case "2" -> showBooksByAuthor();
                case "3" -> addBook();
                case "4" -> removeBook();
                case "5" -> {
                }
            }
        }
    }

    private void showOrderedBooks() throws SQLException {
        List<Books> books = mediaService.getAllBooks();
        books.stream()
                .sorted(new BooksComparator())
                .forEach(System.out::println);
    }

    private void showBooksByAuthor() throws SQLException {
        System.out.println("Please, enter author:");
        Scanner scanner = new Scanner(System.in);
        String author = scanner.nextLine();
        Optional<Books> books = mediaService.getBooksByAuthor(author);
        System.out.println(books);
    }

    private void addBook() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title:");
        String title = scanner.nextLine();
        System.out.println("Enter author:");
        String author = scanner.nextLine();
        System.out.println("Enter genre:");
        String genre = scanner.nextLine();
        System.out.println("Enter number of pages:");
        int pages = Integer.parseInt(scanner.nextLine());
        Media media = new Media(1, "Books");
        Books books = new Books(title, author, genre, pages, media);
        int generatedRow = mediaService.addBook(books);
        if (generatedRow > 1) {
            System.out.println("New book added successfully. Thank you for helping us keep track of new books.");
        } else {
            System.out.println("Unable to add book. ");
        }
    }

    private void removeBook() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title of the book you would like to remove from our list:");
        String title = scanner.nextLine();
        Optional<Books> books = mediaService.getBooksByTitle(title);
        System.out.println("Please confirm this is the correct book: (y/n)");
        System.out.println(books);
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            int generatedRow = mediaService.deleteBook(title);
            if (generatedRow > 1) {
                System.out.println("Book removed successfully");
            } else {
                System.out.println("Unable to remove book. ");
            }
        } else {
            System.out.println("We couldn't find the book you're looking for.");
        }
    }

    private void optionsForMovies(Scanner scanner) throws SQLException {
        String choice = "";
        while (!(choice.equalsIgnoreCase("5"))) {
            System.out.println("1. See all movies (ordered by title)");
            System.out.println("2. See books by director");
            System.out.println("3. Add a movie");
            System.out.println("4. Remove a movie");
            System.out.println("5. Back to main menu");
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showOrderedMovies();
                case "2" -> showBooksByDirector();
                case "3" -> addMovie();
                case "4" -> removeMovie();
                case "5" -> {
                }
            }
        }
    }

    private void showOrderedMovies() throws SQLException {
        List<Movies> movies = mediaService.getAllMovies();
        movies.stream()
                .sorted(new MoviesComparator())
                .forEach(System.out::println);
    }

    private void showBooksByDirector() throws SQLException {
        System.out.println("Please, enter name of director:");
        Scanner scanner = new Scanner(System.in);
        String director = scanner.nextLine();
        Optional<Movies> movies = mediaService.getMoviesByDirector(director);
        System.out.println(movies);
        if (movies.isEmpty()) {
            System.out.println("We couldn't find the movie you're looking for. \n" +
                    "Would like to add it to our library? (y/n)");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                addMovie();
            } else {
                System.out.println("Okay.");
            }
        }
    }

    private void addMovie() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title:");
        String title = scanner.nextLine();
        System.out.println("Enter director:");
        String director = scanner.nextLine();
        System.out.println("Enter genre:");
        String genre = scanner.nextLine();
        System.out.println("Enter ageRating:");
        int ageRating = Integer.parseInt(scanner.nextLine());
        Media media = new Media(2, "Movies");
        Movies movies = new Movies(title, director, genre, ageRating, media);
        int generatedRow = mediaService.addMovie(movies);
        if (generatedRow > 1) {
            System.out.println("New book added successfully. Thank you for helping us keep track of new movies.");
        } else {
            System.out.println("Unable to add movie.");
        }
    }

    private void removeMovie() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title of the movie you would like to remove from our list:");
        String title = scanner.nextLine();
        Optional<Movies> movies = mediaService.getMoviesByTitle(title.toLowerCase());
        System.out.println("Please confirm this is the correct movie: (y/n)");
        System.out.println(movies);
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            int generatedRow = mediaService.deleteMovie(title);
            if (generatedRow < 1) {
                System.out.println("Movie removed successfully");
            } else {
                System.out.println("Unable to remove movie.");
            }
        } else {
            System.out.println("We couldn't find the movie you're looking for.");
        }
    }

    private void optionsForGames(Scanner scanner) throws SQLException {
        String choice = "";
        while (!(choice.equalsIgnoreCase("5"))) {
            System.out.println("1. See all games (ordered by title)");
            System.out.println("2. See games by developer or publisher");
            System.out.println("3. Add a game");
            System.out.println("4. Remove a game");
            System.out.println("5. Back to main menu");
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showOrderedGames();
                case "2" -> showBooksByDeveloperOrPublisher();
                case "3" -> addGame();
                case "4" -> removeGame();
                case "5" -> {
                }
            }
        }
    }

    private void showOrderedGames() throws SQLException {
        List<Games> games = mediaService.getAllGames();
        games.stream()
                .sorted(new GamesComparator())
                .forEach(System.out::println);
    }

    private void showBooksByDeveloperOrPublisher() throws SQLException {
        System.out.println("Please, enter name of developer or publisher:");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        Optional<Games> games = mediaService.getGamesByDeveloperOrPublisher(choice);
        System.out.println(games);
        if (games.isEmpty()) {
            System.out.println("We couldn't find the game you're looking for. \n" +
                    "Would like to add it to our library? (y/n)");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                addGame();
            } else {
                System.out.println("Okay.");
            }
        }
    }

    private void addGame() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title:");
        String title = scanner.nextLine();
        System.out.println("Enter developer:");
        String developer = scanner.nextLine();
        System.out.println("Enter publisher:");
        String publisher = scanner.nextLine();
        System.out.println("Enter ageRating:");
        int ageRating = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter mode (single player/ multiplayer):");
        String mode = scanner.nextLine();
        Media media = new Media(2, "Games");
        Games games = new Games(title, developer, publisher, ageRating, mode, media);
        List<Platform> platforms = new ArrayList<>();
        boolean morePlatforms = true;
        while (morePlatforms) {
            System.out.print("Enter platform");
            System.out.println(" (PlayStation, Nintendo Switch, Microsoft Windows, Xbox, MacOS):");
            String platformName = scanner.nextLine();
            System.out.println("Would you like to add more platforms? (y/n)");
            String answer = scanner.nextLine();
            platforms.add(new Platform(platformName));
            if (answer.equalsIgnoreCase("n")) {
                morePlatforms = false;
            }
        }
        games.setPlatforms(platforms);

        int generatedRow = mediaService.addGame(games, platforms);
        if (generatedRow > 0) {
            System.out.println("New Game added successfully. Thank you for helping us keep track of new games.");
        } else {
            System.out.println("Unable to add game.");
        }
    }

    private void removeGame() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title of the game you would like to remove from our list:");
        String title = scanner.nextLine();
        Optional<Games> games = mediaService.getGamesByTitle(title.toLowerCase());
        System.out.println("Please confirm this is the correct game: (y/n)");
        System.out.println(games);
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            int generatedRow = mediaService.deleteGame(title);
            if (generatedRow == 1) {
                System.out.println("Game removed successfully");
            } else {
                System.out.println("Unable to remove game.");
            }
        } else {
            System.out.println("We couldn't find the game you're looking for.");
        }
    }

    private void searchMedia() throws SQLException {
        System.out.println("Enter media you'd like to search:");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        List<SearchMedia> media = mediaService.searchMedia(userInput);

        if (media.isEmpty()) {
            System.out.println("No results found.");
        } else {
            for (SearchMedia media1 : media) {
                System.out.println(media1);
            }
        }my
    }

    private void quit() {
        System.out.println("Bye and welcome back!");
    }
}
