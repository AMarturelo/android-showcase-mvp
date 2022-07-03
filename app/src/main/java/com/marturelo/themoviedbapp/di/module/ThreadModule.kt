package com.marturelo.themoviedbapp.di.module

import com.marturelo.themoviedbapp.commons.UiThread
import com.marturelo.themoviedbapp.domain.core.executor.JobExecutor
import com.marturelo.themoviedbapp.domain.core.executor.PostExecutionThread
import com.marturelo.themoviedbapp.domain.core.executor.ThreadExecutor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ThreadModule {
    @Singleton
    @Provides
    fun provideThreadExecutor(executor: JobExecutor): ThreadExecutor {
        return executor
    }

    @Singleton
    @Provides
    fun providePostExecutionThread(thread: UiThread): PostExecutionThread {
        return thread
    }
}