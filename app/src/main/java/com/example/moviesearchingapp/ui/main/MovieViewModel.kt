package com.example.moviesearchingapp.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearchingapp.data.MovieRepository
import com.example.moviesearchingapp.model.Movie
import com.example.moviesearchingapp.utils.Constants
import dagger.hilt.android.scopes.ViewScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ViewScoped
class MovieViewModel
@ViewModelInject constructor(private val repository: MovieRepository) : ViewModel() {
    companion object {
        private const val TAG = "BookViewModel"
    }
    init {
        searchMovie(Constants.API_KEY, "avengers")
    }

    val searchedMovies: MutableLiveData<Movie> = MutableLiveData()

    private fun searchMovie(apiKey: String, query: String) {
        repository.searchMovie(Constants.API_KEY, "avenger").enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.d(TAG, "onResponse: response: ${response.body()}")
                Log.d(TAG, "onResponse: response result: ${response.body()?.results?.size}")
                searchedMovies.value = response.body()
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d(TAG, "onFailure: response: ${t.message}")

            }
        })
    }
}