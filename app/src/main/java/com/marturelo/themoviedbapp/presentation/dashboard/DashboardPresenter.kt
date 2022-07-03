package com.marturelo.themoviedbapp.presentation.dashboard

import androidx.annotation.VisibleForTesting
import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.domain.usecase.DiscoveryMoviesUseCase
import com.marturelo.themoviedbapp.presentation.core.AbstractPresenter
import com.marturelo.themoviedbapp.presentation.dashboard.vo.PayloadVO
import com.marturelo.themoviedbapp.presentation.dashboard.vo.toVO
import javax.inject.Inject
import timber.log.Timber

class DashboardPresenter @Inject constructor(
    private val discoveryMoviesUseCase: DiscoveryMoviesUseCase
) : AbstractPresenter<DashboardContract.View>(),
    DashboardContract.Presenter {

    private var internalPayLoad: PayloadVO? = null
        set(value) {
            field = value
            notifyDataChange()
        }

    override val payload: PayloadVO?
        get() = internalPayLoad

    @Inject
    lateinit var navigator: DashboardContract.Navigator

    override fun init() {
        internalPayLoad = PayloadVO(discovery = Constants.DISCOVERY.POPULAR)
        populate()
    }

    override fun restoreFromPayload(payload: PayloadVO) {
        internalPayLoad = payload
    }

    override fun populate() {
        requireNotNull(internalPayLoad)

        internalPayLoad = internalPayLoad?.copy(contentState = DashboardState.LOADING)
        discoveryMoviesUseCase.execute(
            DiscoveryMoviesUseCase.Params(internalPayLoad!!.discovery),
            ::onResult,
            ::onError
        )
    }

    @VisibleForTesting
    fun onResult(result: List<MovieEntity>) {
        requireNotNull(internalPayLoad)

        internalPayLoad = internalPayLoad?.copy(
            contentState = DashboardState.CONTENT,
            items = result.map { it.toVO() })
    }

    @VisibleForTesting
    fun onError(error: Throwable) {
        requireNotNull(payload)

        internalPayLoad = internalPayLoad?.copy(
            contentState = if (internalPayLoad?.items?.isEmpty() == true) DashboardState.ERROR else DashboardState.CONTENT,
        )

        if (internalPayLoad?.items?.isNotEmpty() == true) {
            view?.showError(error)
        }
    }

    @VisibleForTesting
    fun notifyDataChange() {
        payload?.run {
            when (contentState) {
                DashboardState.CONTENT -> {
                    view?.showContentState()
                    view?.updateUI(this)
                }
                DashboardState.LOADING -> {
                    view?.showLoadingState()
                }
                DashboardState.ERROR -> {
                    view?.showErrorState()
                }
                else -> {
                }
            }
        }
    }

    override fun detachView() {
        super.detachView()
        discoveryMoviesUseCase.stop()
    }
}