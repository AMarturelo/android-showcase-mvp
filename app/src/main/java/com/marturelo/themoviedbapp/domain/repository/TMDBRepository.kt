package com.marturelo.themoviedbapp.domain.repository

import com.marturelo.themoviedbapp.commons.utils.DiscoveryDescriptor
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.domain.entity.SourceResultEntity
import io.reactivex.Observable
import io.reactivex.Single

interface TMDBRepository {
    fun discovery(@DiscoveryDescriptor discovery: String): Observable<SourceResultEntity<List<MovieEntity>>>
}