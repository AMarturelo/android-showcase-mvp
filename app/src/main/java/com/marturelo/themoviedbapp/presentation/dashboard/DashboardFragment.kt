package com.marturelo.themoviedbapp.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.marturelo.themoviedbapp.R
import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.ext.setDividerVertical
import com.marturelo.themoviedbapp.presentation.commons.StatefulLayout
import com.marturelo.themoviedbapp.presentation.core.BaseDaggerMVPFragment
import com.marturelo.themoviedbapp.presentation.dashboard.adapter.DashboardController
import com.marturelo.themoviedbapp.presentation.dashboard.vo.PayloadVO
import kotlinx.android.synthetic.main.fragment_dashboard.slDashboard
import kotlinx.android.synthetic.main.fragment_dashboard.srlDashboard
import kotlinx.android.synthetic.main.fragment_dashboard.tvDashboard

class DashboardFragment :
    BaseDaggerMVPFragment<DashboardContract.View, DashboardContract.Presenter>(),
    DashboardContract.View {

    override val layout: Int
        get() = R.layout.fragment_dashboard

    private val controller = DashboardController(itemClickedListener = {
        presenter.onItemClicked(it)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupStatefulLayout()

        restoreStateOrInit(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.payload?.run {
            outState.putParcelable(Constants.InstanceState.DASHBOARD, this)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //presenter.saveInstance(rvAutorizarOperacion.layoutManager?.onSaveInstanceState())
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

    private fun setupRecyclerView() {
        tvDashboard.adapter = controller.adapter
        tvDashboard.setDividerVertical(R.drawable.list_divider)

        srlDashboard.setOnRefreshListener {
            presenter.populate()
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
    }
}

object DashboardState {
    const val CONTENT = StatefulLayout.State.CONTENT
    const val ERROR = "STATE_ERROR"
    const val LOADING = "STATE_LOADING"
}