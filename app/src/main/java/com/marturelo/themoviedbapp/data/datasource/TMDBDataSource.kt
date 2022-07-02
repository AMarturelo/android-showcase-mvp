package com.marturelo.themoviedbapp.data.datasource

import com.marturelo.themoviedbapp.data.model.MovieModel
import io.reactivex.Single

interface TMDBDataSource {
    fun popularMovies(): Single<List<MovieModel>>
    fun topRatedMovies(): Single<List<MovieModel>>
}