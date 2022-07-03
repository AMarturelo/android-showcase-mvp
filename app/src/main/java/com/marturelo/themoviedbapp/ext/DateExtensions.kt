package com.marturelo.themoviedbapp.ext

import java.text.SimpleDateFormat
import java.util.*

fun Date.releasedDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(this)
}