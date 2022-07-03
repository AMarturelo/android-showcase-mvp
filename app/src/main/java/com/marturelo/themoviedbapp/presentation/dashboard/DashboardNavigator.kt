package com.marturelo.themoviedbapp.presentation.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.marturelo.themoviedbapp.R
import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import javax.inject.Inject

class DashboardNavigator @Inject constructor(
    private val fragment: DashboardFragment,
) :
    DashboardContract.Navigator {
    override fun onClose() {
        fragment.requireActivity().finish()
    }

    @SuppressLint("WrongViewCast")
    override fun navigateToDetails(movie: MovieVO) {
        fragment.controller.getViewById(movie.id.toString())?.run {
            val extras = FragmentNavigatorExtras(
                findViewById<ImageView>(R.id.ivMovieCover) to "cover${movie.id}",
                findViewById<ImageView>(R.id.tvMovieTitle) to "title${movie.id}"
            )

            val action = DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(movie)

            NavHostFragment.findNavController(fragment)
                .navigate(
                    action,
                    extras
                )
        }


    }
}