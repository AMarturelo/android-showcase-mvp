package com.marturelo.themoviedbapp.presentation.search.vo

import android.os.Parcelable
import com.marturelo.themoviedbapp.presentation.dashboard.DashboardState
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PayloadVO(
    val items: List<MovieVO> = emptyList(),
    val contentState: String = DashboardState.CONTENT,
    val query: String
) :
    Parcelable