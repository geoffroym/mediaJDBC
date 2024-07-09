import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    private BookRegister bookRegister;
    private final BookService bookService;
    public  Program(BookRegister bookRegister) {
        this.bookRegister = bookRegister;
        bookService = new BookService();
    }
    public void setBookRegister(BookRegister bookRegister){
        this.bookRegister = bookRegister;
    }

    public void run() throws IOException, SQLException {
        System.out.println(UserCommunication.WELCOME);
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 8) {
            System.out.println(UserCommunication.OPTIONS);
            System.out.println("1. Add new book.");
            System.out.println("2. Show all books.");
            System.out.println("3. Books by genre.");
            System.out.println("4. Books by maximum reading time.");
            System.out.println("5. Remove book.");
            System.out.println("6. Books by author.");
            System.out.println("7. Books older than.");
            System.out.println("8. Quit.");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> addBook();
                case 2 -> showAllBooks();
                case 3 -> booksByGenre();
                case 4 -> booksByReadingTime();
                case 5 -> removeBook();
                case 6 -> booksByAuthor();
                case 7 -> booksOlderThan();
                case 8 -> quit();
                default -> System.out.println("Choose a number from 1 - 7.");
            }
        }
    }

    private void booksOlderThan() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter date (YYYY-MM-DD:");
        String date = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(date);
        bookRegister.booksOlderThan(localDate);
    }

    private void quit() throws IOException {
        System.out.println("Bye.");
        bookRegister.writeBookToFile();
    }

    private void booksByAuthor() {
        System.out.println(UserCommunication.ENTER_AUTHOR);
        Scanner scanner = new Scanner(System.in);
        String author = scanner.nextLine();
        bookRegister.booksByAuthor(author);
    }

    private void removeBook() throws SQLException {
        System.out.println("Enter ISBN");
        Scanner scanner = new Scanner(System.in);
        String isbn = scanner.nextLine();
        int rowsAffected = bookService.deleteBook(isbn);
        if (rowsAffected == 1) {
            System.out.println("The book was successfully removed from database");
        } else {
            System.out.println("Unable to delete book.");
        }
        //bookRegister.removeBook(isbn);
    }

    private void booksByReadingTime() {
        System.out.println("Enter maximum minutes:");
        Scanner scanner = new Scanner(System.in);
        int min = Integer.parseInt(scanner.nextLine());
        ArrayList<Book> books = bookRegister.booksByReadingTime(min);
        for (Book b : books){
            System.out.println(b);
        }
    }

    private void booksByGenre() {
        System.out.println("Enter genre:");
        Scanner scanner = new Scanner(System.in);
        Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());
        bookRegister.booksByGenre(genre);
    }

    private void showAllBooks() {
        bookRegister.allBooksInRegister();

    }

    private void addBook() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean validIsbn = false;
        String isbn = null;
        while (!validIsbn){
            System.out.println("Enter ISBN:");
            isbn = scanner.nextLine();
            validIsbn = Utility.validISBN(isbn);
        }
        System.out.println("Enter title:");
        String title = scanner.nextLine();
        System.out.println(UserCommunication.ENTER_AUTHOR);
        String author = scanner.nextLine();
        System.out.println("Enter number of pages:");
        int pages = Integer.parseInt(scanner.nextLine());
        System.out.println(UserCommunication.ENTER_GENRE);
        Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());
        System.out.println("Enter publication date (YYYY-MM-DD:");
        String published = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(published);
        System.out.println("Enter reading minutes per page:");
        int minutes = Integer.parseInt(scanner.nextLine());
        Book book = new Book(isbn, localDate, title, author, pages, genre, minutes);
        ArrayList<Chapter> chapters = new ArrayList<>();
        boolean moreChapters = true;
        while (moreChapters){
            System.out.println("Enter chapter's title:");
            String chapTitle = scanner.nextLine();
            System.out.println("Enter number of pages:");
            int chapNumPag = scanner.nextInt();
            scanner.nextLine();
            Chapter chapter = new Chapter(chapTitle, chapNumPag);
            chapters.add(chapter);
            System.out.println("Would you like to add more chapters? (y/n):");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("n")){
                moreChapters = false;
            }
        }
        book.setChapters(chapters);
        bookRegister.addBook(book);
        int generatedId = bookService.addNewBook(isbn, LocalDate.parse(published), title, author, pages, genre, minutes);
        if (generatedId < 1) {
            System.out.println("New book added successfully.");
        } else {
            System.out.println("Unable to add book.");
        }
    }

}
