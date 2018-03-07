package com.yoursungjin.sjpopmovies.api;

import com.yoursungjin.sjpopmovies.data.MovieDataRoot;
import com.yoursungjin.sjpopmovies.data.detail.MovieDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PopMoviesClient {

    @GET("movie/popular")
    Call<MovieDataRoot> moviesSortByMostPopular(
            @Query("api_key") String api_key
    );

    @GET("movie/top_rated")
    Call<MovieDataRoot> moviesSortByTopRated(
            @Query("api_key") String api_key
    );

    @GET("movie/{movie_id}")
    Call<MovieDetail> movieByID(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );


}

