package com.uttara.test.moviesearch;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mahe on 4/29/2017.
 */

public interface APIEndPoints {
    @GET("search/movie")
    Observable<Results> getMovieDetails(@Query("api_key") String apiKey,
                                        @Query("query") String movieName);
    @GET("movie/popular")
    Observable<Results> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<Results> getRecentMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Observable<Results> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/latest")
    Observable<Results> getLatestMovies(@Query("api_key") String apiKey);


}
