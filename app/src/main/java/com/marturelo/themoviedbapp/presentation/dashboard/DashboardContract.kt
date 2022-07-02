package com.marturelo.themoviedbapp.presentation.dashboard

import com.marturelo.themoviedbapp.presentation.core.MVPContract

interface DashboardContract {
    interface View : MVPContract.BaseView {
    }

    interface Presenter : MVPContract.BasePresenter<View> {
        fun onClosePressed()
    }

    interface Navigator {
        fun onClose()
    }
}