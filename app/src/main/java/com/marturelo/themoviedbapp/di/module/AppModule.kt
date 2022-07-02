package com.marturelo.themoviedbapp.di.module

import com.marturelo.themoviedbapp.presentation.dashboard.DashboardFragment
import com.marturelo.themoviedbapp.presentation.dashboard.di.DashboardModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ContributesAndroidInjector(modules = [DashboardModule::class])
    abstract fun providesDashboardFragment(): DashboardFragment
}