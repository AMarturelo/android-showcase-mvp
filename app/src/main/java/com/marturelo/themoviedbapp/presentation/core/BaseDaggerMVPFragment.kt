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

abstract class BaseDaggerMVPFragment<T : BasePresenter<*>> : DaggerFragment(), BaseView {
    @Inject
    lateinit var presenter: T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onDestroy() {
        presenter.unbindView()
        super.onDestroy()
    }

    @get:LayoutRes
    protected abstract val layout: Int
}