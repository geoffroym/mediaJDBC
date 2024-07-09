public class Chapter {
    private final String chapterTitle;
    private final int chapterPages;

    public Chapter(String chapterTitle, int chapterPages){
        this.chapterTitle = chapterTitle;
        this.chapterPages = chapterPages;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterTitle='" + chapterTitle + '\'' +
                ", chapterPages=" + chapterPages +
                '}';
    }

    public int getChapterPages() {
        return chapterPages;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }
}
