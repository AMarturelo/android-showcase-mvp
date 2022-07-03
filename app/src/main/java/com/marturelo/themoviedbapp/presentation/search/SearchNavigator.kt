package com.marturelo.themoviedbapp.presentation.search

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.marturelo.themoviedbapp.R
import com.marturelo.themoviedbapp.presentation.dashboard.DashboardFragmentDirections
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import javax.inject.Inject

class SearchNavigator @Inject constructor(
    private val fragment: SearchFragment,
) :
    SearchContract.Navigator {
    override fun navigateToBack() {
        NavHostFragment.findNavController(fragment).navigateUp()
    }

    @SuppressLint("WrongViewCast")
    override fun navigateToDetails(movie: MovieVO) {
        fragment.controller.getViewById(movie.id.toString())?.run {
            val extras = FragmentNavigatorExtras(
                findViewById<ImageView>(R.id.ivMovieCover) to "cover${movie.id}",
                findViewById<ImageView>(R.id.tvMovieTitle) to "title${movie.id}"
            )

            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movie)

            NavHostFragment.findNavController(fragment)
                .navigate(
                    action,
                    extras
                )
        }
    }
}