package com.marturelo.themoviedbapp.data.model

data class ResultListModel<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
)