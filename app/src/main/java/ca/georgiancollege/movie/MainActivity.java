package ca.georgiancollege.movie;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import ca.georgiancollege.movie.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MovieAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        binding.searchButton.setOnClickListener(v -> {
            String query = binding.movieInput.getText().toString();
            if (TextUtils.isEmpty(query)) {
                Toast.makeText(this, "Enter a movie name", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.fetchMovies(query);
            }
        });
    }
}