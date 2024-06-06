package com.example.bottom_bar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bottom_bar.bottomBarScreens.HomeScreen
import com.example.bottom_bar.bottomBarScreens.NotificationScreen
import com.example.bottom_bar.bottomBarScreens.ProfileScreen
import com.example.bottom_bar.bottomBarScreens.SearchScreen

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ScreenName.Home.route
    ){
        composable(ScreenName.Home.route){
            HomeScreen(navController = navController)
        }

        composable(ScreenName.Search.route){
            SearchScreen(navController = navController)
        }

        composable(ScreenName.Notifications.route){
            NotificationScreen(navController = navController)
        }

        composable(ScreenName.Profile.route){
            ProfileScreen(navController = navController)
        }
    }
}