package ca.georgiancollege.movie;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies = new ArrayList<>();
}

public void setMovies(List<Movie> movies) {
    this.movies = movies;
    notifyDataSetChanged();
}

@NonNull
@Override
public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_movie, parent, false);
    return new MovieViewHolder(view);
}

