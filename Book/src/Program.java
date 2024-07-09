import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    private BookRegister bookRegister;

    public void setBookRegister(BookRegister bookRegister) {
        this.bookRegister = bookRegister;
    }

    public BookRegister getBookRegister() {
        return bookRegister;
    }

    public void run(){
        System.out.println("Welcome. Here are your options:");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice!= 7){
            System.out.println("1. Add book");
            System.out.println("2. All books");
            System.out.println("3. Books by Genre");
            System.out.println("4. Books by maximum reading time");
            System.out.println("5. Remove book");
            System.out.println("6.Books by author");
            System.out.println("7.Quit");
            choice = scanner.nextInt();
            switch (choice){
                case 1 -> addBook();
                case 2 -> allBooks();
                case 3 -> booksByGenre();
                case 4 -> booksByReadingTime();
                case 5 -> removeBook();
                case 6 -> booksByAuthor();
                case 7 -> quit();
                default -> System.out.println("Choose a number from 1 - 7");
            }
        }
    }

    private void quit() {
        System.out.println("Bye");
    }

    private void booksByAuthor() {
        System.out.println("Enter author:");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        bookRegister.showByAuthor(s);


    }

    private void addBook(){
        //make book
        System.out.println("Enter title:");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        System.out.println("Enter author:");
        String author = scanner.nextLine();
        System.out.println("Enter number of pages:");
        String stringPages = scanner.nextLine();
        int intPages = Integer.parseInt(stringPages);
        System.out.println("Enter genre:");
        Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());
        System.out.println("Enter ISBN:");
        String stringIsbn = scanner.nextLine();
        //Utility utility = new Utility();
/*        if (utility.checkISBN(stringIsbn)){
            int intIsbn = Integer.parseInt(stringIsbn);
        } else {
            System.out.println("Please, enter 10 numbers:");
            String stringIsbn = scanner.nextLine();

        }*/
        while (!Utility.checkISBN(stringIsbn))
        {
            System.out.println("Please, enter 10 numbers:");
            stringIsbn = scanner.nextLine();
        }
        int intIsbn = Integer.parseInt(stringIsbn);
        System.out.println("Enter published date (YYYY-MM-DD)");
        String published = scanner.nextLine();
        System.out.println("Enter minutes per page: ");
        String stringMinutesPages = scanner.nextLine();
        int intMinutesPages = Integer.parseInt(stringMinutesPages);
        Book book = new Book(title, author, intPages, genre, intIsbn, published, intMinutesPages);
        bookRegister.addBook(book);

    }
    private void allBooks(){
        bookRegister.showAllBooks();
    }
    private void booksByGenre(){
        System.out.println("Enter genre:");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine().toUpperCase();
        Genre genre = Genre.valueOf(s);
        bookRegister.showByGenre(genre);

    }
    private void booksByReadingTime(){

    }
    private void removeBook(){
        System.out.println("Enter book you want to remove:");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        bookRegister.removeBook(title);

    }
}
