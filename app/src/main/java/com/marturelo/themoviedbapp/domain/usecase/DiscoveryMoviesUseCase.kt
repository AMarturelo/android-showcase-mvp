package com.marturelo.themoviedbapp.domain.usecase

import com.marturelo.themoviedbapp.domain.core.SingleUseCase
import com.marturelo.themoviedbapp.domain.core.executor.PostExecutionThread
import com.marturelo.themoviedbapp.domain.core.executor.ThreadExecutor
import com.marturelo.themoviedbapp.domain.repository.TMDBRepository
import io.reactivex.Single
import javax.inject.Inject

class DiscoveryMoviesUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val repository: TMDBRepository
) :
    SingleUseCase<String, Unit>(
        threadExecutor,
        postExecutionThread
    ) {
    override fun buildObservable(params: Unit?): Single<String> {
        return repository.discovery()
    }
}