package com.example.bottom_bar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottom_bar.itemList.bottomNavItemsList
import com.example.bottom_bar.navigation.BottomNavigation

@Composable
fun BottomBar() {

    val bottomBarNavController = rememberNavController()
    val navBackStackEntry by bottomBarNavController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {

                bottomNavItemsList.forEach { bottomNavItems ->
                    val isSelected = bottomNavItems.title == navBackStackEntry?.destination?.route

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            bottomBarNavController.navigate(bottomNavItems.title.lowercase()) {
                                popUpTo(bottomBarNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) bottomNavItems.selectedIcon else bottomNavItems.unselectedIcon,
                                contentDescription = bottomNavItems.title
                            )
                        },
                        label = {
                            Text(text = bottomNavItems.title)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        BottomNavigation(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            navController = bottomBarNavController
        )
    }

}