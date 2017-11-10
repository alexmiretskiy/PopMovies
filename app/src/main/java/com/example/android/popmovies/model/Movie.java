package com.example.android.popmovies.model;

public class Movie {

  private String posterPath;

  public Movie(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getPosterPath() {
    return posterPath;
  }
}
