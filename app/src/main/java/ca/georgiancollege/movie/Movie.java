package ca.georgiancollege.movie;

public class Movie {
    private final String title;
    private final String year;
    private final String posterUrl;
    private final String imdbID;
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
    public String getImdbID() { return imdbID; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getPlot() { return plot; }
    public void setPlot(String plot) { this.plot = plot; }
}