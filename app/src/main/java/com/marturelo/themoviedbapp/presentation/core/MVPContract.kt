package com.marturelo.themoviedbapp.presentation.core

interface MVPContract {
    interface BasePresenter<in V: BaseView?> {
        fun attachView(view: V?)
        fun detachView()
    }

    interface BaseView

    interface Navigator
}