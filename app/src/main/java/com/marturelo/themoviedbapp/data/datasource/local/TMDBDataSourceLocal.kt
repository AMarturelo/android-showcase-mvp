package com.marturelo.themoviedbapp.data.datasource.local

import com.marturelo.themoviedbapp.data.datasource.TMDBDataSource
import com.marturelo.themoviedbapp.data.model.MovieModel
import io.reactivex.Single
import javax.inject.Inject

class TMDBDataSourceLocal @Inject constructor(): TMDBDataSource {
    override fun popularMovies(): Single<List<MovieModel>> {
        TODO("Not yet implemented")
    }

    override fun topRatedMovies(): Single<List<MovieModel>> {
        TODO("Not yet implemented")
    }
}