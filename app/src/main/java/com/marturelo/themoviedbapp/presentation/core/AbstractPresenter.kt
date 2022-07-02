package com.marturelo.themoviedbapp.presentation.core

import com.marturelo.themoviedbapp.presentation.core.MVPContract.BaseView
import com.marturelo.themoviedbapp.presentation.core.MVPContract.BasePresenter
import java.lang.ref.WeakReference

abstract class AbstractPresenter<V : BaseView> : BasePresenter<V> {
    var view: WeakReference<V>? = null
    override fun bindView(view: V) {
        this.view = WeakReference(view)
    }

    override fun unbindView() {
        view = null
    }

    override fun getView(): V? {
        return view?.get()
    }
}