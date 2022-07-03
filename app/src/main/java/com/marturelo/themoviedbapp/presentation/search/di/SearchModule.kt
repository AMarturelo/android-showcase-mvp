package com.marturelo.themoviedbapp.presentation.search.di

import com.marturelo.themoviedbapp.presentation.search.SearchContract
import com.marturelo.themoviedbapp.presentation.search.SearchNavigator
import com.marturelo.themoviedbapp.presentation.search.SearchPresenter
import dagger.Module
import dagger.Provides

@Module
class SearchModule {
    @Provides
    fun provideSearchPresenter(presenter: SearchPresenter): SearchContract.Presenter {
        return presenter
    }

    @Provides
    fun provideSearchNavigator(navigator: SearchNavigator): SearchContract.Navigator {
        return navigator
    }
}