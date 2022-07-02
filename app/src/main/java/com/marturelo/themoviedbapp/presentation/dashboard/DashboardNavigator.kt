package com.marturelo.themoviedbapp.presentation.dashboard

import javax.inject.Inject

class DashboardNavigator @Inject constructor(
    val fragment: DashboardFragment,
) :
    DashboardContract.Navigator {
    override fun onClose() {
        fragment.requireActivity().finish()
    }
}