package com.marturelo.themoviedbapp.data.datasource.remote

import com.marturelo.themoviedbapp.data.datasource.TMDBDataSource
import com.marturelo.themoviedbapp.data.model.MovieModel
import io.reactivex.Single
import javax.inject.Inject

class TMDBDataSourceRemote @Inject constructor(): TMDBDataSource {
    override fun popular(): Single<List<MovieModel>> {
        TODO("Not yet implemented")
    }

    override fun topRated(): Single<List<MovieModel>> {
        TODO("Not yet implemented")
    }
}