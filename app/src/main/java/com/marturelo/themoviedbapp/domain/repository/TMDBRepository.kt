package com.marturelo.themoviedbapp.domain.repository

import com.marturelo.themoviedbapp.data.model.MovieModel
import io.reactivex.Single

interface TMDBRepository {
    fun discovery(): Single<List<MovieModel>>
}