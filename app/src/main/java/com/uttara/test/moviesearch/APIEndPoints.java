package com.uttara.test.moviesearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mahe on 4/29/2017.
 */

public interface APIEndPoints {
    @GET("search/movie")
    Call<Results> getMovieDetails(@Query("api_key") String apiKey,
                                            @Query("query") String movieName);
    @GET("movie/popular")
    Call<Results> getPopularMovies(@Query("api_key") String apiKey);


}