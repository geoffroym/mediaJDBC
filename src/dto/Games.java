package dto;

import java.util.List;

public class Games {
    private int id;
    private String title;
    private String developer;
    private String publisher;
    private int ageRating;
    private String mode;
    private Media media;
    private List<Platform> platforms;

    public Games(int id, String title, String developer, String publisher, int ageRating, String mode, Media media, List<Platform> platforms) {
        this.id = id;
        this.title = title;
        this.developer = developer;
        this.publisher = publisher;
        this.ageRating = ageRating;
        this.mode = mode;
        this.media = media;
        this.platforms = platforms;
    }

    public Games(String title, String developer, String publisher, int ageRating, String mode, Media media, List<Platform> platforms) {
        this.title = title;
        this.developer = developer;
        this.publisher = publisher;
        this.ageRating = ageRating;
        this.mode = mode;
        this.media = media;
        this.platforms = platforms;
    }

    public Games(String title, String developer, String publisher, int ageRating, String mode, Media media) {
        this.title = title;
        this.developer = developer;
        this.publisher = publisher;
        this.ageRating = ageRating;
        this.mode = mode;
        this.media = media;
    }

    public Games(String title, String developer, String publisher, int ageRating, String mode) {
        this.title = title;
        this.developer = developer;
        this.publisher = publisher;
        this.ageRating = ageRating;
        this.mode = mode;
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

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    @Override
    public String toString(){
        StringBuilder platformString = new StringBuilder();
        for (Platform platform: platforms){
            platformString.append(platform.getPlatformName()).append(", ");
        }
        if (platformString.length() > 0){
            platformString.setLength(platformString.length() - 2); // remove comma and space
        }
        return String.format(
                "%s - Developer: %s - Publisher: %s - Age rating: %d - mode: %s - Platforms: %s",
                title, developer, publisher, ageRating, mode, platformString);
    }
}
