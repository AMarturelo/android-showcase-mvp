package com.marturelo.themoviedbapp.presentation.details

import com.marturelo.themoviedbapp.presentation.core.MVPContract
import com.marturelo.themoviedbapp.presentation.details.vo.PayloadVO

interface DetailsContract {
    interface View : MVPContract.BaseView {
        fun updateUI(payload: PayloadVO)
        fun showLoadingState()
        fun showContentState()
        fun showErrorState()
        fun showError(error: Throwable)
    }

    interface Presenter : MVPContract.BasePresenter<View?> {
        val payload: PayloadVO?
        fun init()
        fun restoreFromPayload(payload: PayloadVO)
        fun populate()
    }

    interface Navigator {
        fun onClose()
    }
}