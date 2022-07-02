package com.marturelo.themoviedbapp.presentation.dashboard.di

import com.marturelo.themoviedbapp.presentation.dashboard.DashboardContract
import com.marturelo.themoviedbapp.presentation.dashboard.DashboardNavigator
import com.marturelo.themoviedbapp.presentation.dashboard.DashboardPresenter
import dagger.Module
import dagger.Provides

@Module
class DashboardModule {

    @Provides
    fun provideDashboardFactoringPresenter(presenter: DashboardPresenter): DashboardContract.Presenter {
        return presenter
    }

    @Provides
    fun provideDashboardFactoringNavigator(navigator: DashboardNavigator): DashboardContract.Navigator {
        return navigator
    }
}