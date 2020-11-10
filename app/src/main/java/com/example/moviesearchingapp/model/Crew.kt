package com.example.moviesearchingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crew(
    val id: Int,
    val credit_id: String,
    val name: String,
    val profile_path: String?,
    val job: String,
    val department: String
) : Parcelable