package com.marturelo.themoviedbapp.data.repository

import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.commons.utils.DiscoveryDescriptor
import com.marturelo.themoviedbapp.data.datasource.TMDBDataSource
import com.marturelo.themoviedbapp.data.db.dao.MovieDao
import com.marturelo.themoviedbapp.data.model.toEntity
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.domain.entity.SourceResultEntity
import com.marturelo.themoviedbapp.domain.repository.TMDBRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Named

class TMDBRepositoryData @Inject constructor(
    @Named(Constants.DI.TMBD_DS_REMOTE) val remote: TMDBDataSource, val movieDao: MovieDao
) :
    TMDBRepository {
    override fun discovery(@DiscoveryDescriptor discovery: String): Observable<SourceResultEntity<List<MovieEntity>>> {
        val remote = remote.discover(discovery).doOnSuccess {
            movieDao.insertAll(it)
        }.map { result ->
            SourceResultEntity(
                result = result.map { it.toEntity() },
                dataSource = Constants.DataSource.REMOTE,
                networkStatus = Constants.NetworkStatus.NETWORK_STATUS_SUCCESS
            )
        }.onErrorReturn {
            SourceResultEntity(
                result = listOf(),
                error = it,
                dataSource = Constants.DataSource.REMOTE,
                networkStatus = Constants.NetworkStatus.NETWORK_STATUS_ERROR
            )
        }

        val local = movieDao.discovery()

        return remote.toObservable().zipWith(local, BiFunction { remoteResult, localResult ->
            if (remoteResult.isError() && localResult.isEmpty()) {
                throw remoteResult.error!!
            }
            return@BiFunction remoteResult.copy(
                result = localResult.map { it.toEntity() },
                dataSource = if (remoteResult.networkStatus == Constants.NetworkStatus.NETWORK_STATUS_SUCCESS) Constants.DataSource.REMOTE else Constants.DataSource.LOCAL,
            )
        })
    }
}