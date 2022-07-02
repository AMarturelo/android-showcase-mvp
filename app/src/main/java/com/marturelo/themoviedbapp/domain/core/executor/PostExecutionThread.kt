package com.marturelo.themoviedbapp.domain.core.executor

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}