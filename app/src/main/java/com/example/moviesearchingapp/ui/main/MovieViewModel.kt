package com.example.moviesearchingapp.ui.main

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.moviesearchingapp.data.MovieRepository
import com.example.moviesearchingapp.model.Movie
import com.example.moviesearchingapp.model.MovieResponse
import com.example.moviesearchingapp.utils.Constants
import dagger.hilt.android.scopes.ViewScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ViewScoped
class MovieViewModel
@ViewModelInject constructor(
    private val repository: MovieRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TAG = "BookViewModel"
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "avengers"
    }

    init {
        //searchMovie("avenger")
        // getPopularMovies(Constants.API_KEY)
    }

    val searchedMovies: MutableLiveData<List<Movie>> = MutableLiveData()

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val movies = currentQuery.switchMap { queryString ->
        repository.pagingSearch(queryString).cachedIn(viewModelScope)
    }

    fun searchWithQuery(query: String) {
        currentQuery.value = query
    }

    fun getPopularMovies() {
        repository.getPopularMovies(Constants.API_KEY)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    Log.d(TAG, "onResponse: response result: ${response.body()?.results?.size}")
                    searchedMovies.value = response.body()?.results
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: response: ${t.message}")
                }
            })
    }

    fun getTopRatedMovies() {
        repository.getTopRatedMovies(Constants.API_KEY)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    Log.d(TAG, "onResponse: response result: ${response.body()?.results?.size}")
                    searchedMovies.value = response.body()?.results
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: response: ${t.message}")
                }
            })
    }

    fun getUpComingMovies() {
        repository.getUpComingMovies(Constants.API_KEY)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    Log.d(TAG, "onResponse: response result: ${response.body()?.results?.size}")
                    searchedMovies.value = response.body()?.results
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: response: ${t.message}")
                }
            })
    }
}