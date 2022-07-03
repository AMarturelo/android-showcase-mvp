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

    override fun init() {
    }

    override fun restoreFromPayload(payload: PayloadVO) {
    }

    override fun populate() {
    }

    @VisibleForTesting
    fun notifyDataChange() {
        payload?.run {

        }
    }
}