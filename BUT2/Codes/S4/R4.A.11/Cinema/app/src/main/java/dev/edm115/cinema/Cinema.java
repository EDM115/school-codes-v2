package dev.edm115.cinema;

public class Cinema {
    private String imageUrl;
    private String title;
    private String director;
    private int duration;

    public Cinema(String imageUrl, String title, String director, int duration) {
        if (imageUrl == null || title == null || director == null) {
            throw new IllegalArgumentException("imageUrl, title, and director must not be null");
        }
        this.imageUrl = imageUrl;
        this.title = title;
        this.director = director;
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getDuration() {
        return duration;
    }
}
