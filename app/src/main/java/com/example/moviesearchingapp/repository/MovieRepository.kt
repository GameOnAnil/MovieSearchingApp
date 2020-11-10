package com.example.moviesearchingapp.repository

import android.provider.SyncStateContract
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.moviesearchingapp.data.network.MovieApi
import com.example.moviesearchingapp.pagingsource.*
import com.example.moviesearchingapp.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository
@Inject constructor(private val movieApi: MovieApi) {
    companion object {
        private const val TAG = "MovieRepository"
    }

    fun getPopularMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PopularPagingSource(movieApi)
            }
        ).liveData


    fun getTopRatedMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TopRatedPagingSource(movieApi)
            }
        ).liveData


    fun getUpComingMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UpcomingMoviePagingSource(movieApi)
            }
        ).liveData

    fun pagingSearch(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieSearchPagingSource(movieApi, query)
            }
        ).liveData

    fun getCast(movieId: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CreditsPagingSource(movieApi, movieId)

            }
        ).liveData
/*
    fun getCast(movieId: Int) =
        movieApi.getCredits(movieId,Constants.API_KEY)*/

}