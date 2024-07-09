import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRegister {
    private BookProvider bookProvider;
    private List<Book> books;
    public BookRegister(BookProvider bookProvider) throws Exception {
        this.bookProvider = bookProvider;
        books = bookProvider.retrieveBooks();
    }
    public void addBook(Book book){
        books.add(book);
    }
    public void removeBook(String isbn){
        for (Book b: books){
            if (b.getIsbn().equalsIgnoreCase(isbn)){
                books.remove(b);
                return;
            }
        }
    }

    public void allBooksInRegister(){
        for (Book book: books){
            System.out.println(book.toString());
        }
    }

    public void booksByGenre(Genre genre){
        for (Book book: books){
            if(book.getGenre() == genre){
                System.out.println(book);
            }
        }
    }

    public void booksByAuthor(String author){
        for (Book book: books){
            if(book.getAuthor().equals(author)){
                System.out.println(book.toString());
            }
        }
    }

    public ArrayList<Book> booksByReadingTime(int minutes){
        ArrayList<Book> booksToReturn = new ArrayList<>();
        for (Book b: books) {
            if (b.readingTime() <= minutes) {
                booksToReturn.add(b);
            }
        }
        return booksToReturn;
    }

    public void booksOlderThan(LocalDate localDate) {
        for (Book b: books){
            if (b.getPublished().isBefore(localDate)){
                System.out.println(b);
            }
        }
    }

    public void writeBookToFile() throws IOException {
        File file = new File("books.txt");
        file.createNewFile();
        try (FileWriter writer = new FileWriter(file)){
            for (Book b: books){
                writer.write(b.toFileFormat());
                writer.write("---" + "\n");
            }
        }
    }
}
