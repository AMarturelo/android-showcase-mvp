package com.marturelo.themoviedbapp.domain.entity

import java.util.*

data class MovieEntity(
    val adult: Boolean,
    val backdrop_path: String? = null,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String? = null,
    val release_date: Date,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
)