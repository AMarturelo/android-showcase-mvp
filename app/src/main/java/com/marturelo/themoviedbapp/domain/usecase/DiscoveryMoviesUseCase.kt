package com.marturelo.themoviedbapp.domain.usecase

import com.marturelo.themoviedbapp.commons.utils.DiscoveryDescriptor
import com.marturelo.themoviedbapp.domain.core.ObservableUseCase
import com.marturelo.themoviedbapp.domain.core.executor.PostExecutionThread
import com.marturelo.themoviedbapp.domain.core.executor.ThreadExecutor
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.domain.entity.SourceResultEntity
import com.marturelo.themoviedbapp.domain.repository.TMDBRepository
import io.reactivex.Observable
import javax.inject.Inject

class DiscoveryMoviesUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val repository: TMDBRepository
) :
    ObservableUseCase<SourceResultEntity<List<MovieEntity>>, DiscoveryMoviesUseCase.Params>(
        threadExecutor,
        postExecutionThread
    ) {
    override fun buildObservable(params: Params?): Observable<SourceResultEntity<List<MovieEntity>>> {
        requireNotNull(params)
        return repository.discovery(params.discovery)
    }

    data class Params(@DiscoveryDescriptor val discovery: String)
}