package com.marturelo.themoviedbapp.data.datasource.remote

import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.data.api.TMDBApi
import com.marturelo.themoviedbapp.data.datasource.TMDBDataSource
import com.marturelo.themoviedbapp.data.model.MovieModel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class TMDBDataSourceRemote @Inject constructor(
    @Named(Constants.DI.API_KEY) val apiKey: String,
    private val api: TMDBApi
) : TMDBDataSource {
    override fun discover(discovery: String): Single<List<MovieModel>> {
        return api.discoveryMovies(discovery, apiKey).map { it.results }
    }
}