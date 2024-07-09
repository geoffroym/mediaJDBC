package dto;

public class Books {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int pages;
    private Media media;

    public Books(int id, String title, String author, String genre, int pages, Media media) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
        this.media = media;
    }

    public Books(int id, String title, String author, String genre, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
    }

    public Books(String title, String author, String genre, int pages, Media media) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
        this.media = media;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public String toString(){
        return String.format(
                "%s - Author: %s - Genre: %s - pages: %d",
                title, author, genre, pages);
    }
}
