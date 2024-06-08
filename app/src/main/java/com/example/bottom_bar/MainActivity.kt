package com.example.bottom_bar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.bottom_bar.bottomBar.BottomBar
import com.example.bottom_bar.bottomBar.SwipeableBottomBar
import com.example.bottom_bar.itemList.bottomNavItemList
import com.example.bottom_bar.navigation.BottomNavGraph
import com.example.bottom_bar.ui.theme.BottomBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomBarTheme {

                val navController = rememberNavController()
                BottomNavGraph(navController = navController)
                val pagerState = rememberPagerState(pageCount = { bottomNavItemList.size })

                Scaffold(
                    bottomBar = {
                        BottomBar(navController = navController, pagerState = pagerState)
                    }
                ) { paddingValues ->
                    SwipeableBottomBar(pagerState = pagerState, paddingValues = paddingValues, navController = navController)
                }
            }
        }
    }
}