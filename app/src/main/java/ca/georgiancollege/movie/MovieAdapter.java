package ca.georgiancollege.movie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context context;
    private MovieViewModel viewModel;  // fetch details

    public MovieAdapter(Context context, MovieViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());

        Picasso.get()
                .load(movie.getPosterUrl())
                .into(holder.poster);

        holder.itemView.setOnClickListener(v -> {
            viewModel.fetchMovieDetails(movie.getImdbID(), new MovieViewModel.MovieDetailsCallback() {
                @Override
                public void onSuccess(Movie fullMovie) {
                    // Start MovieDetailsActivity with full movie info
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("title", fullMovie.getTitle());
                    intent.putExtra("year", fullMovie.getYear());
                    intent.putExtra("poster", fullMovie.getPosterUrl());
                    intent.putExtra("director", fullMovie.getDirector());
                    intent.putExtra("rating", fullMovie.getRating());
                    intent.putExtra("plot", fullMovie.getPlot());

                    context.startActivity(intent);
                }

                @Override
                public void onFailure(Exception e) {
                    //  error toast on UI
                    ((MainActivity) context).runOnUiThread(() -> {
                        Toast.makeText(context, "Failed to load movie details", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title, year;
        ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            year = itemView.findViewById(R.id.tvYear);
            poster = itemView.findViewById(R.id.ivPoster);
        }
    }
}
