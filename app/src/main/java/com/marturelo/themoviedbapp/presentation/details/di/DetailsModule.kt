package com.marturelo.themoviedbapp.presentation.details.di

import com.marturelo.themoviedbapp.presentation.details.DetailsContract
import com.marturelo.themoviedbapp.presentation.details.DetailsNavigator
import com.marturelo.themoviedbapp.presentation.details.DetailsPresenter
import dagger.Module
import dagger.Provides

@Module
class DetailsModule {
    @Provides
    fun provideDetailsPresenter(presenter: DetailsPresenter): DetailsContract.Presenter {
        return presenter
    }

    @Provides
    fun provideDetailsNavigator(navigator: DetailsNavigator): DetailsContract.Navigator {
        return navigator
    }
}