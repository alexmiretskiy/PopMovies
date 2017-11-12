package com.example.android.popmovies.api;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

  public static final String BASE_URL = "https://api.themoviedb.org/3/";

  private static final int CONNECT_TIMEOUT = 3;

  private static OkHttpClient httpClient = new OkHttpClient.Builder()
      .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
      .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
      .addInterceptor(new LoggingInterceptor())
      .build();

  public static TheMovieDbApi getService() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(TheMovieDbApi.BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    return retrofit.create(TheMovieDbApi.class);
  }
}
