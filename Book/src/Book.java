import java.time.LocalDate;
import java.util.ArrayList;

public class Book {
    //make private instance variables
    private final String title;
    private final String author;
    private final int numberOfPages;
    private final Genre genre;
    private final int isbn;
    private final LocalDate published;
    private final int minutesPerPage;

    public Book(String title, String author, int pages, Genre genre, int isbn, String published, int minutesPerPage) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.published = LocalDate.parse(published);
        this.minutesPerPage = minutesPerPage;
        this.numberOfPages = pages;
    }

    public int getMinutesPerPage() {
        return minutesPerPage;
    }

    public LocalDate getPublished() {
        return published;
    }

    public int getIsbn() {
        return isbn;
    }

    private ArrayList<Chapter> chapters;

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    //make setters and getters
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

    //toString() method makes it more readable, return specific values, without hashcode
    @Override
    public String toString() {
        return "Book{" +
                String.format("Name of the book is %s", title) +
                String.format(" by %s", author) +
                String.format(" Number of pages: %d", numberOfPages) +
                String.format(" Genre: %s", genre) + '\'' +
                String.format(" International Standard Book Number (ISBN): , %d", isbn) +
                String.format(" Published: %s", published) +
                String.format(" Minutes per page: %d", minutesPerPage);
    }
}
