package com.example.jpmorgantest.ui.navigation.route

sealed class MainRoute(path: String) : Route(path) {
    object Detail : MainRoute(DETAIL_PATH)
    object Search : MainRoute(SEARCH_PATH)
    companion object {
        const val SEARCH_PATH = "search"
        const val DETAIL_PATH = "detail"
    }
}
