package ca.georgiancollege.movie;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ca.georgiancollege.movie.databinding.ActivityMovieDetailsBinding;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //data from intent
        String title = getIntent().getStringExtra("title");
        String director = getIntent().getStringExtra("director");
        String year = getIntent().getStringExtra("year");
        String rating = getIntent().getStringExtra("rating");
        String plot = getIntent().getStringExtra("plot");
        String posterUrl = getIntent().getStringExtra("poster");

        binding.tvTitle.setText(title);
        binding.tvDirector.setText("Director: " + director);
        binding.tvYear.setText("Year: " + year);
        binding.tvRating.setText("Rating: " + rating);
        binding.tvPlot.setText(plot);

        Picasso.get()
                .load(posterUrl)
                .into(binding.ivPoster);
    }
}
