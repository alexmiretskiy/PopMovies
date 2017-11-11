package com.example.android.popmovies.model;

import java.util.List;

/**
 * Created by miret on 11.11.2017.
 */

public class MovieDiscoverResponse<T> {

  List<T> results;

  public List<T> getResults() {
    return results;
  }
}