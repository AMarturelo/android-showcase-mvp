package com.marturelo.themoviedbapp.domain.entity

import com.marturelo.themoviedbapp.commons.utils.NetworkStatusDescriptor


data class SourceResultEntity<T>(
    @NetworkStatusDescriptor val networkStatus: Int,
    val error: Throwable? = null,
    val result: T,
    val dataSource: String
) {

    fun isError(): Boolean {
        return error != null
    }
}