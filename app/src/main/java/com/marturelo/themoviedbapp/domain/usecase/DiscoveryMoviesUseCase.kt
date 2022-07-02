package com.marturelo.themoviedbapp.domain.usecase

import com.marturelo.themoviedbapp.domain.core.SingleUseCase
import com.marturelo.themoviedbapp.domain.core.executor.PostExecutionThread
import com.marturelo.themoviedbapp.domain.core.executor.ThreadExecutor
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.domain.repository.TMDBRepository
import io.reactivex.Single
import javax.inject.Inject

class DiscoveryMoviesUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val repository: TMDBRepository
) :
    SingleUseCase<List<MovieEntity>, Unit>(
        threadExecutor,
        postExecutionThread
    ) {
    override fun buildObservable(params: Unit?): Single<List<MovieEntity>> {
        return repository.discovery()
    }
}