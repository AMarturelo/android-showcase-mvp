package com.marturelo.themoviedbapp.presentation.dashboard.vo

import android.os.Parcelable
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import java.util.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieVO(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: Date,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable {
    val posterUrl: String
        get() {
            return "https://image.tmdb.org/t/p/w600_and_h900_bestv2/$poster_path"
        }
}

fun MovieEntity.toVO(): MovieVO {
    return MovieVO(
        adult,
        backdrop_path,
        genre_ids,
        id,
        original_language,
        original_title,
        overview,
        popularity,
        poster_path,
        release_date,
        title,
        video,
        vote_average,
        vote_count
    )
}