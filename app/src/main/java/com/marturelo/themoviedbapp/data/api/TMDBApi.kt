package com.marturelo.themoviedbapp.data.api

import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.commons.utils.Constants.API.API_KEY_PARAM
import com.marturelo.themoviedbapp.commons.utils.Constants.API.DISCOVERY_PATH
import com.marturelo.themoviedbapp.commons.utils.Constants.API.MOVIE_PATH
import com.marturelo.themoviedbapp.commons.utils.Constants.API.QUERY_PARAM
import com.marturelo.themoviedbapp.commons.utils.Constants.API.SEARCH_PATH
import com.marturelo.themoviedbapp.data.model.MovieModel
import com.marturelo.themoviedbapp.data.model.ResultListModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET(MOVIE_PATH + DISCOVERY_PATH)
    fun discoveryMovies(
        @Path(value = Constants.API.DISCOVERY, encoded = true) discovery: String,
        @Query(API_KEY_PARAM) apiKey: String,
    ): Single<ResultListModel<MovieModel>>

    @GET(SEARCH_PATH)
    fun searchMovies(
        @Query(API_KEY_PARAM) apiKey: String,
        @Query(QUERY_PARAM) query: String,
    ): Single<ResultListModel<MovieModel>>
}