package com.example.bottom_bar.navigation

sealed class ScreenName(val route: String) {
    data object Home : ScreenName(route = "home")
    data object Search : ScreenName(route = "search")
    data object Notifications : ScreenName(route = "notifications")
    data object Profile : ScreenName(route = "profile")
}