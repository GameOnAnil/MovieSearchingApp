package com.example.moviesearchingapp.data.network

import com.example.moviesearchingapp.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieResponse>


    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("movie/upcoming")
    fun getUpComingMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>
}