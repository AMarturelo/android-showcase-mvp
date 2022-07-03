package com.marturelo.themoviedbapp.presentation.search

import androidx.annotation.VisibleForTesting
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.domain.entity.SourceResultEntity
import com.marturelo.themoviedbapp.domain.usecase.SearchMoviesUseCase
import com.marturelo.themoviedbapp.presentation.commons.StatefulLayout
import com.marturelo.themoviedbapp.presentation.core.AbstractPresenter
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import com.marturelo.themoviedbapp.presentation.dashboard.vo.toVO
import com.marturelo.themoviedbapp.presentation.search.vo.PayloadVO
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : AbstractPresenter<SearchContract.View>(),
    SearchContract.Presenter {
    private var internalPayLoad: PayloadVO? = null
        set(value) {
            field = value
            notifyDataChange()
        }

    override val payload: PayloadVO?
        get() = internalPayLoad

    @Inject
    lateinit var navigator: SearchContract.Navigator

    override fun init() {
        internalPayLoad = PayloadVO(query = "")
        populate()
    }

    override fun restoreFromPayload(payload: PayloadVO) {
        internalPayLoad = payload
    }

    override fun onBackClicked() {

    }

    override fun restore() {
        notifyDataChange()
    }

    override fun onItemClicked(it: MovieVO) {
        navigator.navigateToDetails(it)
    }

    override fun onQueryChanged(query: String) {
        internalPayLoad = payload?.copy(query = query)
        populate()
    }

    override fun populate() {
        internalPayLoad?.run {
            internalPayLoad =
                copy(contentState = if (items.isEmpty()) SearchState.LOADING else contentState)
            searchMoviesUseCase.execute(
                SearchMoviesUseCase.Params(query),
                ::onResult,
                ::onError
            )
        }
    }

    @VisibleForTesting
    fun onResult(result: SourceResultEntity<List<MovieEntity>>) {
        requireNotNull(internalPayLoad)

        if (result.isError()) {
            view?.showError(result.error!!)
        }

        internalPayLoad = internalPayLoad?.copy(
            contentState = SearchState.CONTENT,
            items = result.result.map { it.toVO() })
    }

    @VisibleForTesting
    fun onError(error: Throwable) {
        requireNotNull(payload)

        internalPayLoad = internalPayLoad?.copy(
            contentState = if (internalPayLoad?.items?.isEmpty() == true) SearchState.ERROR else SearchState.CONTENT,
        )

        if (internalPayLoad?.items?.isNotEmpty() == true) {
            view?.showError(error)
        }
    }

    @VisibleForTesting
    fun notifyDataChange() {
        payload?.run {
            when (contentState) {
                SearchState.CONTENT -> {
                    view?.showContentState()
                    view?.updateUI(this)
                }
                SearchState.LOADING -> {
                    view?.showLoadingState()
                }
                SearchState.ERROR -> {
                    view?.showErrorState()
                }
                else -> {
                }
            }
        }
    }

    override fun detachView() {
        super.detachView()
        searchMoviesUseCase.stop()
    }
}

object SearchState {
    const val CONTENT = StatefulLayout.State.CONTENT
    const val ERROR = "STATE_ERROR"
    const val LOADING = "STATE_LOADING"
}