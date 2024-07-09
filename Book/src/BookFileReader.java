import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class BookFileReader {
    private ArrayList<Book> readBooksFromFile() throws FileNotFoundException {
        ArrayList<Book> booksFromFile = new ArrayList<>();
        File file = new File("books.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            String isbn = scanner.nextLine();
            String publishedDate = scanner.nextLine();
            String title = scanner.nextLine();
            String author = scanner.nextLine();
            String nrOfPages = scanner.nextLine();
            String genre = scanner.nextLine();
            String minutesPerPage = scanner.nextLine();
            String numberOfChapters = scanner.nextLine();
            int nrChapters = Integer.parseInt(numberOfChapters);
            Book b = new Book(title, author, Integer.parseInt(nrOfPages), Genre.valueOf(genre), Integer.parseInt(publishedDate), isbn, Integer.parseInt(minutesPerPage));
        }

    }
}
