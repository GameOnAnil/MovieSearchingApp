package com.example.moviesearchingapp.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import com.example.moviesearchingapp.data.network.MovieApi
import com.example.moviesearchingapp.model.Cast
import com.example.moviesearchingapp.model.Crew
import com.example.moviesearchingapp.model.Movie
import com.example.moviesearchingapp.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class CreditsPagingSource(
    private val movieApi: MovieApi,
    private val movieId: Int
) : PagingSource<Int, Cast>(
) {
    companion object {
        private const val TAG = "CreditsPagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cast> {
        val position = params.key ?: 1

        return try {
            val response = movieApi.getCredits(movieId, Constants.API_KEY)
            Log.d(TAG, "load: response: $response")
            val cast = response.cast
            Log.d(TAG, "load: castSize: ${cast.size}")
            LoadResult.Page(
                data = cast,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (cast.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}