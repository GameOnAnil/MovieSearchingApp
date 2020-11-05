package com.example.moviesearchingapp.ui

import com.example.moviesearchingapp.data.network.MovieApi
import com.example.moviesearchingapp.model.Movie
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository
@Inject constructor(private val movieApi: MovieApi) {

    fun searchMovie(apiKey: String, query: String): Call<Movie> {
        return movieApi.searchMovie(apiKey, query)
    }
}