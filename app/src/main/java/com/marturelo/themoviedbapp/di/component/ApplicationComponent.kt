package com.marturelo.themoviedbapp.di.component

import com.marturelo.themoviedbapp.TheMovieDBApp
import com.marturelo.themoviedbapp.di.module.AppModule
import com.marturelo.themoviedbapp.di.module.DataModule
import com.marturelo.themoviedbapp.di.module.NetworkModule
import com.marturelo.themoviedbapp.di.module.ThreadModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ThreadModule::class,
        DataModule::class,
        NetworkModule::class,
    ]
)
interface ApplicationComponent : AndroidInjector<TheMovieDBApp> {
    @Component.Factory
    interface Builder : AndroidInjector.Factory<TheMovieDBApp>
}