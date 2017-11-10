package com.example.android.popmovies.utilities;

import android.net.Uri;
import com.example.android.popmovies.BuildConfig;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

  final static String BASE_URL = "https://api.themoviedb.org/3";
  public final static String PARAM_POPULAR = "/movie/popular";
  final static String PARAM_TOP_RATED = "/movie/top_rated";
  final static String PARAM_API_KEY = "api_key";

  public static URL buildUrl(String typesOfPopularity) {
    Uri builtUri = Uri.parse(BASE_URL + typesOfPopularity).buildUpon()
        .appendQueryParameter(PARAM_API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
        .build();
    URL url = null;
    try {
      url = new URL(builtUri.toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return url;
  }

  public static String getResponseFromHttpUrl(URL url) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    try {
      InputStream in = urlConnection.getInputStream();

      Scanner scanner = new Scanner(in);
      scanner.useDelimiter("\\A");

      boolean hasInput = scanner.hasNext();
      if (hasInput) {
        return scanner.next();
      } else {
        return null;
      }
    } finally {
      urlConnection.disconnect();
    }
  }
}