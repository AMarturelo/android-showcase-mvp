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

        const val MOVIE_PATH = "movie/"
        const val SEARCH_PATH = "search/movie"
        const val DISCOVERY = "discovery"
        const val API_KEY_PARAM = "api_key"
        const val QUERY_PARAM = "query"
        const val DISCOVERY_PATH = "{$DISCOVERY}"
    }

    object Arg {
        const val MOVIE = "movie"
    }

    object Hero {
        const val SEARCH_VIEW = "search_view"
    }

    object InstanceState {
        const val DASHBOARD = "dashboard_saved_instance"
        const val SEARCH = "search_saved_instance"
        const val DETAILS = "details_saved_instance"
    }

    object DISCOVERY {
        const val POPULAR = "popular"
        const val TOP_RARED = "top_rated"
    }

    object NetworkStatus {
        const val NETWORK_STATUS_PURE = 0
        const val NETWORK_STATUS_SUCCESS = 1
        const val NETWORK_STATUS_IN_PROCESS = 2
        const val NETWORK_STATUS_ERROR = 3
    }

    object DataSource{
        const val LOCAL = "local"
        const val REMOTE = "remote"
    }
}