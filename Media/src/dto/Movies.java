package dto;

public class Movies {
    private int id;
    private String title;
    private String director;
    private String genre;
    private int ageRating;
    private Media media;

    public Movies(int id, String title, String director, String genre, int ageRating, Media media) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.ageRating = ageRating;
        this.media = media;
    }

    public Movies(String title, String director, String genre, int ageRating) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    public Movies(String title, String director, String genre, int ageRating, Media media) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.ageRating = ageRating;
        this.media = media;
    }

    public Movies(int id, String title, String director, String genre, int ageRating) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.ageRating = ageRating;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return String.format(
                "%s - Director: %s - Genre: %s - Age Rating: %d",
                title, director, genre, ageRating);
    }
}
