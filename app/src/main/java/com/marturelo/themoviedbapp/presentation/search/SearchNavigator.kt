package com.marturelo.themoviedbapp.presentation.search

import androidx.navigation.fragment.NavHostFragment
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import javax.inject.Inject

class SearchNavigator @Inject constructor(
    private val fragment: SearchFragment,
) :
    SearchContract.Navigator {
    override fun navigateToBack() {
        NavHostFragment.findNavController(fragment).navigateUp()
    }

    override fun navigateToDetails(movie: MovieVO) {

    }
}