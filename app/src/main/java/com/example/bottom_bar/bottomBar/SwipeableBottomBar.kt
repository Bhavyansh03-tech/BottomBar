package com.example.bottom_bar.bottomBar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bottom_bar.itemList.bottomNavItemList
import com.example.bottom_bar.screens.HomeScreen
import com.example.bottom_bar.screens.NotificationScreen
import com.example.bottom_bar.screens.ProfileScreen
import com.example.bottom_bar.screens.SearchScreen

@Composable
fun SwipeableBottomBar(
    pagerState: PagerState,
    paddingValues: PaddingValues,
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        userScrollEnabled = true
    ) { page ->
        when (page) {
            0 -> HomeScreen(navController = navController)
            1 -> SearchScreen(navController = navController)
            2 -> NotificationScreen(navController = navController)
            3 -> ProfileScreen(navController = navController)
        }
    }

    // Observe the pager state and update the navigation bar accordingly
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            val newRoute = bottomNavItemList[page].title.lowercase()
            if (currentRoute != newRoute) {
                navController.navigate(newRoute) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

}