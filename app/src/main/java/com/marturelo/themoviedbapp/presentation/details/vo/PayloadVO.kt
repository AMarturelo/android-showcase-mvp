package com.marturelo.themoviedbapp.presentation.details.vo

import android.os.Parcelable
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PayloadVO(val movie: MovieVO) : Parcelable