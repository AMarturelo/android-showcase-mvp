package com.marturelo.themoviedbapp.presentation.commons.utils

import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import java.util.*

object FakeValuesEntity {
    fun movie(): MovieEntity {
        return MovieEntity(
            false, "Fake", (1000..4000).random(), "Fake", "Fake", "Fake", 5.0, "Fake",
            Date(),
            "Fake",
            true,
            45.1,
            1,
            "Fake",
        )
    }

    fun movies(): List<MovieEntity> {
        return (0..10).map { movie() }
    }
}