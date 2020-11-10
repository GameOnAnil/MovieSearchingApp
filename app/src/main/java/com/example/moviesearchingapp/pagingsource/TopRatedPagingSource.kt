package com.example.moviesearchingapp.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import com.example.moviesearchingapp.data.network.MovieApi
import com.example.moviesearchingapp.model.Movie
import com.example.moviesearchingapp.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class TopRatedPagingSource(
    private val movieApi: MovieApi
) : PagingSource<Int, Movie>(
) {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1

        return try {
            val response = movieApi.getTopRatedMovies(Constants.API_KEY, position)
            val movies = response.results
            LoadResult.Page(
                data = movies,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}