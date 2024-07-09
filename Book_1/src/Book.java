import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class Book {
    private String title;
    private String author;
    private int numberOfPages;
    private Genre genre;
    private List<Chapter> chapters;
    private String isbn;
    private LocalDate published;

    public int getMinutesPerPage() {
        return minutesPerPage;
    }

    public void setMinutesPerPage(int minutesPerPage) {
        this.minutesPerPage = minutesPerPage;
    }

    private int minutesPerPage;

    public Book(String isbn, LocalDate published, String title, String author, int numberOfPages, Genre genre, int minutesPerPage, List<Chapter> chapters) {
        this.title = title;
        this.author = author;
        this.numberOfPages = numberOfPages;
        this.genre = genre;
        this.chapters = chapters;
        this.isbn = isbn;
        this.published = published;
        this.minutesPerPage = minutesPerPage;
    }
    public Book(String isbn, LocalDate published, String title, String author, int numberOfPages, Genre genre, int minutesPerPage) {
        this.title = title;
        this.author = author;
        this.numberOfPages = numberOfPages;
        this.genre = genre;
        this.isbn = isbn;
        this.published = published;
        this.minutesPerPage = minutesPerPage;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", genre=" + genre +
                ", chapters=" + chapters +
                ", isbn=" + isbn +
                ", published=" + published +
                ", minutesPerPage=" + minutesPerPage +
                '}';
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getPublished() {
        return published;
    }

    public int readingTime(){
        return numberOfPages * minutesPerPage;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }
    public String toFileFormat(){
        StringBuilder s = new StringBuilder(isbn + '\n' +
                published + '\n' +
                title + '\n' +
                author + '\n' +
                numberOfPages + '\n' +
                genre + '\n' +
                minutesPerPage + '\n' +
                chapters.size() +  '\n');
        for (Chapter c : chapters){
            s.append(c.toFileFormat());
        }
        return s.toString();
    }
}
