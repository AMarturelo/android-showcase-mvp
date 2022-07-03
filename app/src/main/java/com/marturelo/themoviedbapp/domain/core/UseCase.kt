package com.marturelo.themoviedbapp.domain.core

interface UseCase<I, Params> {
    fun execute(callback: I, params: Params?)
    fun stop()
}