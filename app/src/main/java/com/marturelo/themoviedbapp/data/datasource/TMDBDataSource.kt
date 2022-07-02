package com.marturelo.themoviedbapp.data.datasource

import com.marturelo.themoviedbapp.data.model.MovieModel
import io.reactivex.Single

interface TMDBDataSource {
    fun popular(): Single<List<MovieModel>>
    fun topRated(): Single<List<MovieModel>>
}