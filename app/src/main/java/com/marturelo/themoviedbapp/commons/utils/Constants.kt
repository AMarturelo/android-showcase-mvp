package com.marturelo.themoviedbapp.commons.utils

interface Constants {
    object DI {
        const val TMBD_DS_REMOTE = "TMBD_DS_REMOTE"
        const val TMBD_DS_LOCAL = "TMBD_DS_LOCAL"
        const val HOST = "HOST"
        const val API_KEY = "API_KEY"
    }

    object API {
        const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        const val API_CONNECTION_TIMEOUT = 5L

        const val MOVIE_PATH =
            "movie/"
        const val POPULAR_PATH = "popular/"
        const val TOP_RATED_PATH = "top_rated/"
        const val DISCOVERY = "discovery"
        const val DISCOVERY_PATH = "{$DISCOVERY}"
    }

    object Arg {
        const val MOVIE = "movie"
    }

    object InstanceState {
        const val DASHBOARD = "dashboard_saved_instance"
        const val DETAILS = "details_saved_instance"
    }

    object DISCOVERY {
        const val POPULAR = "popular"
        const val TOP_RARED = "top_rated"
    }
}