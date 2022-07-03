package com.marturelo.themoviedbapp.presentation.dashboard.vo

import android.os.Parcelable
import com.marturelo.themoviedbapp.presentation.dashboard.DashboardState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PayloadVO(
    val items: List<MovieVO> = emptyList(),
    val contentState: String = DashboardState.CONTENT,
    val discovery: String
) :
    Parcelable