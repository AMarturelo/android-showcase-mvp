package com.marturelo.themoviedbapp.di.module

import com.marturelo.themoviedbapp.presentation.dashboard.DashboardFragment
import com.marturelo.themoviedbapp.presentation.dashboard.di.DashboardModule
import com.marturelo.themoviedbapp.presentation.details.DetailsFragment
import com.marturelo.themoviedbapp.presentation.details.di.DetailsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ContributesAndroidInjector(modules = [DashboardModule::class])
    abstract fun providesDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector(modules = [DetailsModule::class])
    abstract fun providesDetailsFragment(): DetailsFragment
}