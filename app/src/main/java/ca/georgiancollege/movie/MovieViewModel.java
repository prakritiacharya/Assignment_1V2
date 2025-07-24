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

public class MovieViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final OkHttpClient client = new OkHttpClient();

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void fetchMovies(String query) {
        String apiKey = "YOUR_API_KEY";  // Replace with your OMDb API key
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
                        JSONArray results = json.getJSONArray("Search");
                        List<Movie> movieList = new ArrayList<>();

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject item = results.getJSONObject(i);
                            Movie movie = new Movie(
                                    item.getString("Title"),
                                    item.getString("Year"),
                                    item.getString("Poster")
                            );
                            movieList.add(movie);
                        }
                        movies.postValue(movieList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
