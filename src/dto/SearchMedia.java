package dto;

public class SearchMedia {
    private String mediaType;
    private String title;
    private String creators;

    public SearchMedia(String mediaType, String title, String creators) {
        this.mediaType = mediaType;
        this.title = title;
        this.creators = creators;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreators() {
        return creators;
    }

    public void setCreators(String creators) {
        this.creators = creators;
    }

    @Override
    public String toString(){
        return String.format("%s: %s - %s", mediaType, title, creators);
    }
}
