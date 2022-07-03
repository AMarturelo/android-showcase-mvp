package com.marturelo.themoviedbapp.presentation.search

import com.marturelo.themoviedbapp.presentation.core.MVPContract
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import com.marturelo.themoviedbapp.presentation.search.vo.PayloadVO

interface SearchContract {
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
        fun onBackClicked()
        fun restore()
        fun populate()
        fun onItemClicked(it: MovieVO)
        fun onQueryChanged(query: String)
    }

    interface Navigator {
        fun navigateToBack()
        fun navigateToDetails(movie: MovieVO)
    }
}