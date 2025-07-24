package ca.georgiancollege.movie;

public class Movie {
    private final String title;
    private final String year;
    private final String posterUrl;
    private String imdbID;
    private String director;
    private String rating;
    private String plot;

    public Movie(String title, String year, String posterUrl, String imdbID) {
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
        this.imdbID = imdbID;
    }

    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getPosterUrl() { return posterUrl; }
}