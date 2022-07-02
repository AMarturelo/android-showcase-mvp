package com.marturelo.themoviedbapp.presentation.dashboard

import com.marturelo.themoviedbapp.domain.usecase.DiscoveryMoviesUseCase
import com.marturelo.themoviedbapp.presentation.core.AbstractPresenter
import javax.inject.Inject

class DashboardPresenter @Inject constructor(
    private val discoveryMoviesUseCase: DiscoveryMoviesUseCase
) : AbstractPresenter<DashboardContract.View>(),
    DashboardContract.Presenter {

    @Inject
    lateinit var navigator: DashboardContract.Navigator

    override fun onClosePressed() {
        //navigator.onClose()
        discoveryMoviesUseCase.execute(::onResult, ::onError)
    }

    fun onResult(resutl: String) {

    }

    fun onError(error: Throwable) {

    }

    override fun unbindView() {
        super.unbindView()
        discoveryMoviesUseCase.stop()
    }
}