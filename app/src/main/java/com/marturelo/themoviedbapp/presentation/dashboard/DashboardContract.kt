package com.marturelo.themoviedbapp.presentation.dashboard

import com.marturelo.themoviedbapp.presentation.core.MVPContract
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import com.marturelo.themoviedbapp.presentation.dashboard.vo.PayloadVO

interface DashboardContract {
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
        fun onItemClicked(it: MovieVO)
        fun restore()
    }

    interface Navigator {
        fun onClose()
        fun navigateToDetails(movie: MovieVO)
    }
}