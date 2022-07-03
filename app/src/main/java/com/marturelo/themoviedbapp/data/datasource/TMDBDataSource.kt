package com.marturelo.themoviedbapp.data.datasource

import com.marturelo.themoviedbapp.data.model.MovieModel
import io.reactivex.Single

interface TMDBDataSource {
    fun discover(discovery: String): Single<List<MovieModel>>
}