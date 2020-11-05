package com.example.moviesearchingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val page: Int,
    val total_pages: Int,
    val total_result: Int
) : Parcelable