package com.example.moviesearchingapp.pagingsource

import androidx.paging.PagingSource
import com.example.moviesearchingapp.data.network.MovieApi
import com.example.moviesearchingapp.model.Movie
import com.example.moviesearchingapp.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class MovieSearchPagingSource(
    private val api: MovieApi,
    private val query: String
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1

        return try {
            val response = api.searchMovie(Constants.API_KEY, query, position)
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

    companion object {
        private const val TAG = "MovieSearchPagingSource"
    }
}