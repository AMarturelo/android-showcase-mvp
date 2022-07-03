package com.marturelo.themoviedbapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.ext.toDate
import java.util.*

@Entity(tableName = "movies")
data class MovieModel(
    val adult: Boolean,
    val backdrop_path: String? = null,
    @PrimaryKey
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String? = null,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
)

fun MovieModel.toEntity(): MovieEntity {
    return MovieEntity(
        adult,
        backdrop_path,
        id,
        original_language,
        original_title,
        overview,
        popularity,
        poster_path,
        release_date.toDate(),
        title,
        video,
        vote_average,
        vote_count,
    )
}