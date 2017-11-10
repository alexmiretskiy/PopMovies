package com.example.android.popmovies.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.android.popmovies.R;
import com.example.android.popmovies.model.Movie;
import com.example.android.popmovies.utilities.NetworkUtils;
import com.example.android.popmovies.utilities.OpenMoviesJsonUtils;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private MoviesAdapter moviesAdapter;
  private TextView errorTextView;
  private ProgressBar loadingIndicator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    recyclerView = (RecyclerView) findViewById(R.id.rv_posters);
    errorTextView = (TextView) findViewById(R.id.tv_error_message_display);

    GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
        2);

    recyclerView.setLayoutManager(gridLayoutManager);

    recyclerView.setHasFixedSize(true);

    moviesAdapter = new MoviesAdapter();

    recyclerView.setAdapter(moviesAdapter);

    loadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

    loadMoviesData();
  }

  private void loadMoviesData() {
    showMoviesDataView();

    new FetchMoviesTask().execute(NetworkUtils.PARAM_POPULAR);
  }

  private void showMoviesDataView() {
    errorTextView.setVisibility(View.INVISIBLE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  private void showErrorMessage() {
    errorTextView.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.INVISIBLE);
  }

  public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
      if (strings.length == 0) {
        return null;
      }

      String typesOfPopularity = strings[0];
      URL moviesRequestUrl = NetworkUtils.buildUrl(typesOfPopularity);
      try {
        String jsonWeatherResponse = NetworkUtils
            .getResponseFromHttpUrl(moviesRequestUrl);

        return OpenMoviesJsonUtils.extractMoviesFromJson(jsonWeatherResponse);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
      loadingIndicator.setVisibility(View.INVISIBLE);
      if (movies != null) {
        showMoviesDataView();
        moviesAdapter.setMoviesData(movies);
      } else {
        showErrorMessage();
      }
    }
  }
}
