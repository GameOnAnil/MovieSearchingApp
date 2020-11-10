package com.example.moviesearchingapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.moviesearchingapp.repository.MovieRepository
import dagger.hilt.android.scopes.ViewScoped

@ViewScoped
class MovieViewModel
@ViewModelInject constructor(
    private val repository: MovieRepository,

    ) : ViewModel() {
    companion object {
        private const val TAG = "BookViewModel"
    }

    val upcomingMovies = repository.getUpComingMovies().cachedIn(viewModelScope)
    val topRatedMovies = repository.getTopRatedMovies().cachedIn(viewModelScope)
    val popularMovies = repository.getPopularMovies().cachedIn(viewModelScope)


}