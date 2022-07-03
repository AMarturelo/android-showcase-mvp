package com.marturelo.themoviedbapp.di.module

import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.data.datasource.TMDBDataSource
import com.marturelo.themoviedbapp.data.datasource.remote.TMDBDataSourceRemote
import com.marturelo.themoviedbapp.data.repository.TMDBRepositoryData
import com.marturelo.themoviedbapp.domain.repository.TMDBRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Named

@Module
class DataModule {
    @Provides
    @Reusable
    @Named(Constants.DI.TMBD_DS_REMOTE)
    fun provideRemoteDataSource(remote: TMDBDataSourceRemote): TMDBDataSource {
        return remote
    }

    @Provides
    @Reusable
    fun provideRepository(tmdbRepositoryData: TMDBRepositoryData): TMDBRepository {
        return tmdbRepositoryData
    }
}