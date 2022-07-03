package com.marturelo.themoviedbapp.di.module

import android.content.Context
import com.marturelo.themoviedbapp.TheMovieDBApp
import com.marturelo.themoviedbapp.presentation.dashboard.DashboardFragment
import com.marturelo.themoviedbapp.presentation.dashboard.di.DashboardModule
import com.marturelo.themoviedbapp.presentation.details.DetailsFragment
import com.marturelo.themoviedbapp.presentation.details.di.DetailsModule
import com.marturelo.themoviedbapp.presentation.search.SearchFragment
import com.marturelo.themoviedbapp.presentation.search.di.SearchModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideContext(application: TheMovieDBApp): Context

    @ContributesAndroidInjector(modules = [DashboardModule::class])
    abstract fun providesDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector(modules = [DetailsModule::class])
    abstract fun providesDetailsFragment(): DetailsFragment

    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun providesSearchFragment(): SearchFragment
}