package com.marturelo.themoviedbapp.presentation.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.marturelo.themoviedbapp.R
import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.ext.setDividerVertical
import com.marturelo.themoviedbapp.presentation.commons.SegmentView
import com.marturelo.themoviedbapp.presentation.core.BaseDaggerMVPFragment
import com.marturelo.themoviedbapp.presentation.dashboard.adapter.DashboardController
import com.marturelo.themoviedbapp.presentation.dashboard.vo.DiscoveryVO
import com.marturelo.themoviedbapp.presentation.dashboard.vo.PayloadVO
import kotlinx.android.synthetic.main.fragment_dashboard.rvDashboard
import kotlinx.android.synthetic.main.fragment_dashboard.slDashboard
import kotlinx.android.synthetic.main.fragment_dashboard.srlDashboard
import kotlinx.android.synthetic.main.layout_dashboard_categories.svDashboard
import kotlinx.android.synthetic.main.layout_dashboard_search.tvSearch

class DashboardFragment :
    BaseDaggerMVPFragment<DashboardContract.View, DashboardContract.Presenter>(),
    DashboardContract.View {



    override val layout: Int
        get() = R.layout.fragment_dashboard

    lateinit var controller: DashboardController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupStatefulLayout()
        setupSearchView()
        setupSegmented()

        if (presenter.payload != null) {
            presenter.restore()
        } else {
            restoreStateOrInit(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.payload?.run {
            outState.putParcelable(Constants.InstanceState.DASHBOARD, this)
        }
        super.onSaveInstanceState(outState)
    }

    private fun restoreStateOrInit(savedInstanceState: Bundle? = null) {
        if (savedInstanceState?.containsKey(Constants.InstanceState.DASHBOARD) == true) {
            val payload: PayloadVO =
                savedInstanceState.getParcelable(Constants.InstanceState.DASHBOARD)!!
            presenter.restoreFromPayload(payload)
        } else {
            presenter.init()
        }
    }

    @SuppressLint("CheckResult")
    private fun setupSearchView() {
        tvSearch.setOnClickListener {
            presenter.onSearchClicked()
        }
        tvSearch.transitionName = Constants.Hero.SEARCH_VIEW
    }

    private fun setupRecyclerView() {
        controller = DashboardController(
            itemClickedListener = {
                presenter.onItemClicked(it)
            },
            rvDashboard
        )
        rvDashboard.adapter = controller.adapter
        rvDashboard.setDividerVertical(R.drawable.list_divider)

        srlDashboard.setOnRefreshListener {
            presenter.populate()
        }
    }

    private fun setupSegmented(){
        svDashboard.onSegmentSelected = {
            presenter.onDiscoverySelected(it)
        }
    }

    private fun setupStatefulLayout() {
        slDashboard.setStateView(
            DashboardState.ERROR,
            LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_dashboard_state_error,
                null
            )
        )

        slDashboard.setStateView(
            DashboardState.LOADING,
            LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_dashboard_state_loading,
                null
            )
        )
    }

    override fun updateUI(payload: PayloadVO) {
        controller.setData(payload)

        svDashboard.withVO(
            Constants.DISCOVERY.discoveries.map {
                SegmentView.SegmentVO(it.title, isSelected = it.discovery == payload.discovery)
            }
        )
    }

    override fun showLoadingState() {
        slDashboard.state = DashboardState.LOADING
    }

    override fun showContentState() {
        slDashboard.state = DashboardState.CONTENT
        srlDashboard.isRefreshing = false
    }

    override fun showErrorState() {
        slDashboard.state = DashboardState.ERROR
    }

    override fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message ?: "Remote error", Toast.LENGTH_SHORT).show()
    }
}

