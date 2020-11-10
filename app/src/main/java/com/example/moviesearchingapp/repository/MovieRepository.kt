package com.example.moviesearchingapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.moviesearchingapp.pagingsource.MovieSearchPagingSource
import com.example.moviesearchingapp.data.network.MovieApi
import com.example.moviesearchingapp.pagingsource.PopularPagingSource
import com.example.moviesearchingapp.pagingsource.TopRatedPagingSource
import com.example.moviesearchingapp.pagingsource.UpcomingMoviePagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository
@Inject constructor(private val movieApi: MovieApi) {

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
}