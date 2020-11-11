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
import com.example.moviesearchingapp.model.Crew
import com.example.moviesearchingapp.model.CrewResponse
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
    companion object {
        private const val TAG = "DetailsViewModel"
    }

    lateinit var castDetail: LiveData<PagingData<Cast>>
    var crewDetail: MutableLiveData<List<Crew>> = MutableLiveData()

    fun getCast(movieId: Int) {
        castDetail = repository.getCast(movieId).cachedIn(viewModelScope)
    }

    fun getCrew(movieId: Int) {
        repository.getCrew(movieId).enqueue(object : Callback<CrewResponse> {
            override fun onResponse(call: Call<CrewResponse>, response: Response<CrewResponse>) {
                crewDetail.value = response.body()?.crew
            }

            override fun onFailure(call: Call<CrewResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }


}
