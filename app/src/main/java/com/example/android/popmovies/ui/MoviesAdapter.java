package com.example.android.popmovies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.android.popmovies.R;
import com.example.android.popmovies.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

  private final static String POSTER_BASE_URL = "https://image.tmdb.org/t/p/";

  private final static String POSTER_SIZE = "w342";

  private List<Movie> moviesData;

   MoviesAdapter(List<Movie> moviesData) {
    this.moviesData = moviesData;
  }

  @Override
  public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    int layoutIdForListItem = R.layout.poster_list_item;
    LayoutInflater inflater = LayoutInflater.from(context);
    boolean shouldAttachToParentImmediately = false;

    View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
    return new MoviesAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
    String posterPath = moviesData.get(position).getPosterPath();
    Picasso.with(holder.itemView.getContext()).load(POSTER_BASE_URL + POSTER_SIZE  + posterPath)
        .into(holder.posterImageView);
  }

  @Override
  public int getItemCount() {
    if (null == moviesData) {
      return 0;
    }
    return moviesData.size();
  }

  class MoviesAdapterViewHolder extends RecyclerView.ViewHolder {

    final ImageView posterImageView;

    public MoviesAdapterViewHolder(View itemView) {
      super(itemView);
      posterImageView = (ImageView) itemView.findViewById(R.id.iv_poster);
    }
  }


}
