package com.marturelo.themoviedbapp.presentation.dashboard

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.marturelo.themoviedbapp.R
import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import javax.inject.Inject

class DashboardNavigator @Inject constructor(
    val fragment: DashboardFragment,
) :
    DashboardContract.Navigator {
    override fun onClose() {
        fragment.requireActivity().finish()
    }

    override fun navigateToDetails(movie: MovieVO) {
        NavHostFragment.findNavController(fragment)
            .navigate(R.id.action_dashboardFragment_to_detailsFragment,
                Bundle().apply {
                    putParcelable(Constants.Arg.MOVIE, movie)
                }
            )
    }
}