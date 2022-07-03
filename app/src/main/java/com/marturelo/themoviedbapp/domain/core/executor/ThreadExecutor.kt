package com.marturelo.themoviedbapp.domain.core.executor

import io.reactivex.Scheduler

interface ThreadExecutor {
    val scheduler: Scheduler
}