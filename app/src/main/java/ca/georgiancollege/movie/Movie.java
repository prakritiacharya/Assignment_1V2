package ca.georgiancollege.movie;

public class Movie {
    private final String title;
    private final String year;
    private final String posterUrl;

    public Movie(String title, String year, String posterUrl) {
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
    }
}