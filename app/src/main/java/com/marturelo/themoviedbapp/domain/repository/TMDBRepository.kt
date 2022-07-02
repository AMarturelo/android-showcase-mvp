package com.marturelo.themoviedbapp.domain.repository

import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import io.reactivex.Single

interface TMDBRepository {
    fun discovery(): Single<List<MovieEntity>>
}