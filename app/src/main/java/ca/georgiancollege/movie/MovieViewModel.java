package ca.georgiancollege.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.util.Log;

public class MovieViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final OkHttpClient client = new OkHttpClient();

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void fetchMovies(String query) {
        String apiKey = "77ef3890";
        String url = "https://www.omdbapi.com/?apikey=" + apiKey + "&s=" + query;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        if (json.has("Search")) {
                            JSONArray results = json.getJSONArray("Search");
                            List<Movie> movieList = new ArrayList<>();

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject item = results.getJSONObject(i);
                                Movie movie = new Movie(
                                        item.getString("Title"),
                                        item.getString("Year"),
                                        item.getString("Poster"),
                                        item.getString("imdbID")
                                );
                                movieList.add(movie);
                            }
                            Log.d("MovieViewModel", "Movies found: " + movieList.size());
                            movies.postValue(movieList);
                        } else {
                            movies.postValue(new ArrayList<>());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        movies.postValue(new ArrayList<>());
                    }
                }
            }
        });
    }

    public void fetchMovieDetails(String imdbID, MovieDetailsCallback callback) {
        String apiKey = "77ef3890";
        String url = "https://www.omdbapi.com/?apikey=" + apiKey + "&i=" + imdbID + "&plot=full";

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());

                        // Parse full details
                        Movie movie = new Movie(
                                json.getString("Title"),
                                json.getString("Year"),
                                json.getString("Poster"),
                                json.getString("imdbID")
                        );
                        movie.setDirector(json.optString("Director", "N/A"));
                        movie.setRating(json.optString("imdbRating", "N/A"));
                        movie.setPlot(json.optString("Plot", "N/A"));

                        callback.onSuccess(movie);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(e);
                    }
                } else {
                    callback.onFailure(new IOException("Response not successful"));
                }
            }
        });
    }

    public interface MovieDetailsCallback {
        void onSuccess(Movie movie);
        void onFailure(Exception e);
    }
}


