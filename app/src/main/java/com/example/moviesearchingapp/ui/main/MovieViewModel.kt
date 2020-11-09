package com.example.moviesearchingapp.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesearchingapp.data.MovieRepository
import com.example.moviesearchingapp.model.Movie
import dagger.hilt.android.scopes.ViewScoped

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

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    /*

           init {
               movies.value = repository.getUpComingMovies()
                   .cachedIn(viewModelScope).value
           }
    */
    val upcomingMovies = repository.getUpComingMovies().cachedIn(viewModelScope)
    val topRatedMovies = repository.getTopRatedMovies().cachedIn(viewModelScope)
    val popularMovies = repository.getPopularMovies().cachedIn(viewModelScope)


    fun searchWithQuery(query: String) {
        currentQuery.value = query
    }

    /* fun getPopularMovies() {
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
     }*/


}