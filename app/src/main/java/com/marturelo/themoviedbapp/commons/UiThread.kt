package com.marturelo.themoviedbapp.commons

import com.marturelo.themoviedbapp.domain.core.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UiThread @Inject constructor() : PostExecutionThread {
    override val scheduler: Scheduler
        get() {
            return AndroidSchedulers.mainThread()
        }
}