package com.example.android.popmovies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.android.popmovies.BuildConfig;
import com.example.android.popmovies.R;
import com.example.android.popmovies.api.ServiceGenerator;
import com.example.android.popmovies.api.TheMovieDbApi;
import com.example.android.popmovies.model.Movie;
import com.example.android.popmovies.model.MovieDiscoverResponse;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  TheMovieDbApi theMovieDbApi;

  private RecyclerView recyclerView;
  private TextView errorTextView;
  private ProgressBar loadingIndicator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    recyclerView = (RecyclerView) findViewById(R.id.rv_posters);
    errorTextView = (TextView) findViewById(R.id.tv_error_message_display);

    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

    recyclerView.setLayoutManager(gridLayoutManager);

    recyclerView.setHasFixedSize(true);

    loadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    loadingIndicator.setVisibility(View.VISIBLE);

    showMoviesDataView();

    loadMovies();
  }

  private void loadMovies() {
    Call<MovieDiscoverResponse<Movie>> call = ServiceGenerator.getService()
        .getData(BuildConfig.THE_MOVIE_DB_API_KEY, TheMovieDbApi.SORT_BY_POPULARITY);

    call.enqueue(new Callback<MovieDiscoverResponse<Movie>>() {
      @Override
      public void onResponse(Call<MovieDiscoverResponse<Movie>> call,
          Response<MovieDiscoverResponse<Movie>> response) {
        if (response.isSuccessful()) {
          List<Movie> movies = new ArrayList<>();
          movies.addAll(response.body().getResults());
          recyclerView.setAdapter(new MoviesAdapter(movies));
          loadingIndicator.setVisibility(View.INVISIBLE);
        } else {
          loadingIndicator.setVisibility(View.INVISIBLE);
          showErrorMessage();
          Log.d("QuestionsCallback",
              "Code: " + response.code() + " Message: " + response.message());
        }
      }

      @Override
      public void onFailure(Call<MovieDiscoverResponse<Movie>> call, Throwable t) {
        t.printStackTrace();
      }
    });
  }

  Callback<MovieDiscoverResponse<Movie>> movieCallback = new Callback<MovieDiscoverResponse<Movie>>() {
    @Override
    public void onResponse(Call<MovieDiscoverResponse<Movie>> call,
        Response<MovieDiscoverResponse<Movie>> response) {
      if (response.isSuccessful()) {
        List<Movie> movies = new ArrayList<>();
        movies.addAll(response.body().getResults());
        recyclerView.setAdapter(new MoviesAdapter(movies));
        loadingIndicator.setVisibility(View.INVISIBLE);
      } else {
        loadingIndicator.setVisibility(View.INVISIBLE);
        showErrorMessage();
        Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
      }
    }

    @Override
    public void onFailure(Call<MovieDiscoverResponse<Movie>> call, Throwable t) {
      t.printStackTrace();
    }
  };

  private void showMoviesDataView() {
    errorTextView.setVisibility(View.INVISIBLE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  private void showErrorMessage() {
    errorTextView.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.INVISIBLE);
  }
}
