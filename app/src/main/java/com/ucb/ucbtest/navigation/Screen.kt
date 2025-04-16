package com.ucb.ucbtest.navigation

sealed class Screen(val route: String) {
    object BooksScreen: Screen("book")
}