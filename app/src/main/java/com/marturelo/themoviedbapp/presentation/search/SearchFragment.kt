package com.marturelo.themoviedbapp.presentation.search

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.transition.TransitionInflater
import com.marturelo.themoviedbapp.R
import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.commons.utils.Constants.Hero.SEARCH_VIEW
import com.marturelo.themoviedbapp.ext.rxFocus
import com.marturelo.themoviedbapp.ext.rxQuery
import com.marturelo.themoviedbapp.ext.setDividerVertical
import com.marturelo.themoviedbapp.presentation.core.BaseDaggerMVPFragment
import com.marturelo.themoviedbapp.presentation.dashboard.DashboardState
import com.marturelo.themoviedbapp.presentation.search.adapter.SearchController
import com.marturelo.themoviedbapp.presentation.search.vo.PayloadVO
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindUntilEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.fragment_search.rvSearch
import kotlinx.android.synthetic.main.fragment_search.slSearch
import kotlinx.android.synthetic.main.fragment_search.srlSearch
import kotlinx.android.synthetic.main.fragment_search.svSearch
import kotlinx.android.synthetic.main.layout_search.view.searchView

class SearchFragment :
    BaseDaggerMVPFragment<SearchContract.View, SearchContract.Presenter>(),
    SearchContract.View {

    override val layout: Int
        get() = R.layout.fragment_search

    lateinit var controller: SearchController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupStatefulLayout()
        setupSearchView()
        setupHero()

        if (presenter.payload != null) {
            presenter.restore()
        } else {
            restoreStateOrInit(savedInstanceState)
        }
    }

    private fun setupHero() {
        svSearch.transitionName = SEARCH_VIEW
    }

    @SuppressLint("CheckResult")
    private fun setupSearchView() {
        svSearch.rxQuery()
            .distinctUntilChanged()
            .debounce(500, TimeUnit.MILLISECONDS)
            .bindUntilEvent(this, Lifecycle.Event.ON_PAUSE)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenter.onQueryChanged(it)
            }

        svSearch.rxFocus()
            .debounce(100, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .bindUntilEvent<Boolean?>(this, Lifecycle.Event.ON_PAUSE)
            .subscribe {
                if (it == false) {
                    presenter.onFocusLost()
                }
            }
    }

    private fun setupStatefulLayout() {
        slSearch.setStateView(
            DashboardState.ERROR,
            LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_dashboard_state_error,
                null
            )
        )

        slSearch.setStateView(
            DashboardState.LOADING,
            LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_dashboard_state_loading,
                null
            )
        )
    }

    private fun setupRecyclerView() {
        controller = SearchController(
            itemClickedListener = {
                presenter.onItemClicked(it)
            },
            rvSearch
        )
        rvSearch.adapter = controller.adapter
        rvSearch.setDividerVertical(R.drawable.list_divider)

        srlSearch.setOnRefreshListener {
            presenter.populate()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.payload?.run {
            outState.putParcelable(Constants.InstanceState.SEARCH, this)
        }
        super.onSaveInstanceState(outState)
    }

    private fun restoreStateOrInit(savedInstanceState: Bundle? = null) {
        if (savedInstanceState?.containsKey(Constants.InstanceState.SEARCH) == true) {
            val payload: PayloadVO =
                savedInstanceState.getParcelable(Constants.InstanceState.SEARCH)!!
            presenter.restoreFromPayload(payload)
        } else {
            presenter.init()
        }
    }

    override fun updateUI(payload: PayloadVO) {
        controller.setData(payload)
    }

    override fun showLoadingState() {
        slSearch.state = SearchState.LOADING
    }

    override fun showContentState() {
        slSearch.state = SearchState.CONTENT
        srlSearch.isRefreshing = false
    }

    override fun showErrorState() {
        slSearch.state = SearchState.ERROR
    }

    override fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message ?: "Remote error", Toast.LENGTH_SHORT).show()
    }
}