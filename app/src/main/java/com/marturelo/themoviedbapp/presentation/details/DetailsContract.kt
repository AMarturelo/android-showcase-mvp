package com.marturelo.themoviedbapp.presentation.details

import com.marturelo.themoviedbapp.presentation.core.MVPContract
import com.marturelo.themoviedbapp.presentation.details.vo.PayloadVO

interface DetailsContract {
    interface View : MVPContract.BaseView {
        fun updateUI(payload: PayloadVO)
    }

    interface Presenter : MVPContract.BasePresenter<View?> {
        val payload: PayloadVO?
        fun initWithPayload(payload: PayloadVO)
        fun restoreFromPayload(payload: PayloadVO)
        fun onBackClicked()
        fun restore()
    }

    interface Navigator {
        fun navigateToBack()
    }
}