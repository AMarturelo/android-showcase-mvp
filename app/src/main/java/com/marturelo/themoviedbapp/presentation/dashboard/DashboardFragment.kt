package com.marturelo.themoviedbapp.presentation.dashboard

import android.os.Bundle
import android.view.View
import com.marturelo.themoviedbapp.R
import com.marturelo.themoviedbapp.TheMovieDBApp
import com.marturelo.themoviedbapp.presentation.core.BaseDaggerMVPFragment
import kotlinx.android.synthetic.main.fragment_dashboard.button

class DashboardFragment : BaseDaggerMVPFragment<DashboardContract.Presenter>(),
    DashboardContract.View {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            presenter.onClosePressed()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_dashboard
}