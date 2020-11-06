package com.example.moviesearchingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val page: Int,
    val total_pages: Int,
    val total_result: Int,
    val results: List<Results>
) : Parcelable {

    @Parcelize
    data class Results(
        val id: Int,
        val title: String,
        val poster_path: String? = null,
        val adult: Boolean,
        val release_date: String,
        val backdrop_path: String,
        val vote_average: Float,
        val overview: String
    ) : Parcelable
}