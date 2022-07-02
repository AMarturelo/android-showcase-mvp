package com.marturelo.themoviedbapp.presentation.core

interface MVPContract {
    interface BasePresenter<V : BaseView> {
        fun bindView(view: V)
        fun unbindView()
        fun getView(): V?
    }

    interface BaseView

    interface Navigator
}