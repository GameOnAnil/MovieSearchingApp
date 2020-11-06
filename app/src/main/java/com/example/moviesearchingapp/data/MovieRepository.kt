package com.example.moviesearchingapp.data

import com.example.moviesearchingapp.data.network.MovieApi
import com.example.moviesearchingapp.model.MovieResponse
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository
@Inject constructor(private val movieApi: MovieApi) {

    fun searchMovie(apiKey: String, query: String): Call<MovieResponse> {
        return movieApi.searchMovie(apiKey, query)
    }
}