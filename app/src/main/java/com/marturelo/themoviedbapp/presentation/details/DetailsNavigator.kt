package com.marturelo.themoviedbapp.presentation.details

import javax.inject.Inject

class DetailsNavigator @Inject constructor(
    val fragment: DetailsFragment,
) :
    DetailsContract.Navigator {
    override fun onClose() {
        fragment.requireActivity().finish()
    }
}