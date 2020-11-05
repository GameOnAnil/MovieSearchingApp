package com.example.moviesearchingapp.data.network

import com.example.moviesearchingapp.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<Movie>
}