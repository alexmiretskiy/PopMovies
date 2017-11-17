package com.example.android.popmovies.api;

import com.example.android.popmovies.model.Movie;
import com.example.android.popmovies.model.MovieDiscoverResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDbApi {

  String SORT_BY_POPULARITY = "popularity.desc";

  @GET("discover/movie")
  Call<MovieDiscoverResponse<Movie>> getData(@Query("api_key") String resourceApiKey,
      @Query("sort_by") String resourceSortBy);
}

