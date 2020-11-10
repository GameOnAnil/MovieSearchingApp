package com.example.moviesearchingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
    val cast_id: Int,
    val name: String,
    val profile_path: String?,
    val credit_id: String,
    val gender: Int?,
    val character: String
) : Parcelable