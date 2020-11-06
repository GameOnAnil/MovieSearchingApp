package com.example.moviesearchingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    val page: Int,
    val total_pages: Int,
    val total_result: Int,
    val results: List<Movie>
) : Parcelable