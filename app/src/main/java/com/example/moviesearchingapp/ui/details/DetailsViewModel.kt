package com.example.moviesearchingapp.ui.details

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesearchingapp.model.Cast
import com.example.moviesearchingapp.model.CreditResponse
import com.example.moviesearchingapp.repository.MovieRepository
import dagger.hilt.android.scopes.ViewScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ViewScoped
class DetailsViewModel
@ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    lateinit var crewDetail: LiveData<PagingData<Cast>>

    fun getCast(movieId: Int) {
        crewDetail = repository.getCast(movieId).cachedIn(viewModelScope)
    }

}