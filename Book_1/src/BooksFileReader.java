import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BooksFileReader implements BookProvider{
    @Override
    public List<Book> retrieveBooks() throws FileNotFoundException {
        ArrayList<Book> booksFromFile = new ArrayList<>();
        File file = new File("books.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            String isnb = scanner.nextLine();
            LocalDate published = LocalDate.parse(scanner.nextLine());
            String title = scanner.nextLine();
            String author = scanner.nextLine();
            int numberOfPages = Integer.parseInt(scanner.nextLine());
            Genre genre = Genre.valueOf(scanner.nextLine());
            int minutesPerPage = Integer.parseInt(scanner.nextLine());
            String nrOfChaptersString = scanner.nextLine();
            int nrOfChapters = Integer.parseInt(nrOfChaptersString);
            Book b = new Book(isnb, published, title, author, numberOfPages, genre, minutesPerPage);
            ArrayList<Chapter> chapters = new ArrayList<>();
            for (int i = 0; i < nrOfChapters; i++){
                String chapTitle = scanner.nextLine();
                String nrOfPagesChap = scanner.nextLine();
                Chapter c = new Chapter(chapTitle, Integer.parseInt(nrOfPagesChap));
                chapters.add(c);
            }
            b.setChapters(chapters);
            booksFromFile.add(b);
            scanner.nextLine();
        }
        return booksFromFile;
    }
}
