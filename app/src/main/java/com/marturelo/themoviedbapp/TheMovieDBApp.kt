package com.marturelo.themoviedbapp

import com.marturelo.themoviedbapp.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber


open class TheMovieDBApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?>? {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}