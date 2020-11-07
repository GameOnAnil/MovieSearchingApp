package com.example.moviesearchingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val adult: Boolean,
    val release_date: String,
    val backdrop_path: String?,
    val vote_average: Float,
    val overview: String
) : Parcelable