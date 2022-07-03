package com.marturelo.themoviedbapp.presentation.details

import androidx.navigation.fragment.NavHostFragment
import javax.inject.Inject

class DetailsNavigator @Inject constructor(
    private val fragment: DetailsFragment,
) :
    DetailsContract.Navigator {
    override fun navigateToBack() {
        NavHostFragment.findNavController(fragment).popBackStack()
    }
}