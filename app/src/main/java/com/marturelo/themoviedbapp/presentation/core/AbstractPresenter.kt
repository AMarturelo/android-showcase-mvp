package com.marturelo.themoviedbapp.presentation.core

import com.marturelo.themoviedbapp.presentation.core.MVPContract.BaseView
import com.marturelo.themoviedbapp.presentation.core.MVPContract.BasePresenter
import java.lang.ref.WeakReference

abstract class AbstractPresenter<V : BaseView?> : BasePresenter<V?> {
    private var mView: WeakReference<V>? = null
    val view: V?
        get() = mView?.get()

    override fun attachView(view: V?) {
        mView = WeakReference<V>(view)
    }

    override fun detachView() {
        mView = null
    }
}