package com.marturelo.themoviedbapp.data.repository

import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.data.datasource.TMDBDataSource
import com.marturelo.themoviedbapp.data.model.MovieModel
import com.marturelo.themoviedbapp.domain.repository.TMDBRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class TMDBRepositoryData @Inject constructor(
    @Named(Constants.DI.TMBD_DS_REMOTE) val remote: TMDBDataSource,
    @Named(Constants.DI.TMBD_DS_LOCAL) local: TMDBDataSource
) :
    TMDBRepository {
    override fun discovery(): Single<List<MovieModel>> {
        return remote.popular()
    }
}