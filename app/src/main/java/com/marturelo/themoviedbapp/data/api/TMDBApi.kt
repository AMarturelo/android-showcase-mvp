package com.marturelo.themoviedbapp.data.api

import com.marturelo.themoviedbapp.commons.utils.Constants.API.MOVIE_PATH
import com.marturelo.themoviedbapp.commons.utils.Constants.API.POPULAR_PATH
import com.marturelo.themoviedbapp.commons.utils.Constants.API.TOP_RATED_PATH
import com.marturelo.themoviedbapp.data.model.MovieModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {
    @GET(MOVIE_PATH + POPULAR_PATH)
    fun popularMovies(
        @Query("api_key") apiKey: String
    ): Single<List<MovieModel>>

    @GET(MOVIE_PATH + TOP_RATED_PATH)
    fun topRatedMovies(
        @Query("api_key") apiKey: String
    ): Single<List<MovieModel>>
}