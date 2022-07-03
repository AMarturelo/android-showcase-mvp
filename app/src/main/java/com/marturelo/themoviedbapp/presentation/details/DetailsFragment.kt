package com.marturelo.themoviedbapp.presentation.details

import com.marturelo.themoviedbapp.R
import com.marturelo.themoviedbapp.presentation.core.BaseDaggerMVPFragment
import com.marturelo.themoviedbapp.presentation.details.vo.PayloadVO

class DetailsFragment :
    BaseDaggerMVPFragment<DetailsContract.View, DetailsContract.Presenter>(),
    DetailsContract.View {

    override val layout: Int
        get() = R.layout.fragment_details

    override fun updateUI(payload: PayloadVO) {
    }

    override fun showLoadingState() {
    }

    override fun showContentState() {
    }

    override fun showErrorState() {
    }

    override fun showError(error: Throwable) {
    }
}