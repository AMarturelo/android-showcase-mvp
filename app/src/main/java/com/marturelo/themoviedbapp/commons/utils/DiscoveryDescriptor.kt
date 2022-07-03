package com.marturelo.themoviedbapp.commons.utils

import androidx.annotation.StringDef

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@StringDef(Constants.DISCOVERY.POPULAR, Constants.DISCOVERY.TOP_RARED)
@Retention(AnnotationRetention.SOURCE)
annotation class DiscoveryDescriptor