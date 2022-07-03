package com.marturelo.themoviedbapp.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.marturelo.themoviedbapp.presentation.core.MVPContract.BasePresenter
import com.marturelo.themoviedbapp.presentation.core.MVPContract.BaseView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseDaggerMVPFragment<in V : BaseView?, T : BasePresenter<V?>> : DaggerFragment(), BaseView {
    @Inject
    lateinit var presenter: T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout, container, false)

        presenter.attachView(this as V)
        super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    @get:LayoutRes
    protected abstract val layout: Int
}