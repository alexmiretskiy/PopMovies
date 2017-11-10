package com.example.android.popmovies.utilities;

import android.text.TextUtils;
import android.util.Log;
import com.example.android.popmovies.model.Movie;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class OpenMoviesJsonUtils {

  public static List<Movie> extractMoviesFromJson(String moviesJSON) {
    if (TextUtils.isEmpty(moviesJSON)) {
      return null;
    }

    List<Movie> movies = new ArrayList<>();

    try {
      JSONObject baseJsonResponse = new JSONObject(moviesJSON);

      JSONArray moviesArray = baseJsonResponse.getJSONArray("results");

      for (int i = 0; i < moviesArray.length(); i++) {
        JSONObject currentMovie = moviesArray.getJSONObject(i);

        String posterPath = currentMovie.getString("poster_path");

        Movie movie = new Movie(posterPath);
        movies.add(movie);
      }
    } catch (JSONException e) {
      Log.e("Log", "Problem parsing the book JSON results", e);
    }
    return movies;
  }
}
