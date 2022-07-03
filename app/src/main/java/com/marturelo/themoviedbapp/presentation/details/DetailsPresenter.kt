package com.marturelo.themoviedbapp.presentation.details

import androidx.annotation.VisibleForTesting
import com.marturelo.themoviedbapp.presentation.core.AbstractPresenter
import com.marturelo.themoviedbapp.presentation.details.vo.PayloadVO
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
) : AbstractPresenter<DetailsContract.View>(),
    DetailsContract.Presenter {

    private var internalPayLoad: PayloadVO? = null
        set(value) {
            field = value
            notifyDataChange()
        }

    override val payload: PayloadVO?
        get() = internalPayLoad

    @Inject
    lateinit var navigator: DetailsContract.Navigator

    override fun initWithPayload(payload: PayloadVO) {
        internalPayLoad = payload
    }

    override fun restoreFromPayload(payload: PayloadVO) {
        internalPayLoad = payload
    }

    override fun restore() {
        notifyDataChange()
    }

    override fun onBackClicked() {
        navigator.navigateToBack()
    }

    @VisibleForTesting
    fun notifyDataChange() {
        payload?.run {
            view?.updateUI(this)
        }
    }
}