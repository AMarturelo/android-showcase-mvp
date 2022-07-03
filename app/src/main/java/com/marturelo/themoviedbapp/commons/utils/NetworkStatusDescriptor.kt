package com.marturelo.themoviedbapp.commons.utils

import androidx.annotation.IntDef

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@IntDef(
    Constants.NetworkStatus.NETWORK_STATUS_PURE,
    Constants.NetworkStatus.NETWORK_STATUS_IN_PROCESS,
    Constants.NetworkStatus.NETWORK_STATUS_ERROR,
    Constants.NetworkStatus.NETWORK_STATUS_SUCCESS
)
@Retention(AnnotationRetention.SOURCE)
annotation class NetworkStatusDescriptor