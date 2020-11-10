package com.example.moviesearchingapp.model


data class CreditResponse(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
)