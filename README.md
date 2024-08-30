# Bottom Navigation with Swipeable Screens in Jetpack Compose

This repository showcases an Android application featuring a bottom navigation bar that navigates between screens. Additionally, it implements a horizontal pager to make the screens swipeable, all using Jetpack Compose and Kotlin.

## Features

- **Bottom Navigation Bar**: Navigate between different screens using a bottom navigation bar.
- **Swipeable Screens**: Swipe horizontally to switch between screens using a horizontal pager.
- **Jetpack Compose**: Utilizes Jetpack Compose for modern, declarative UI design.
- **Kotlin**: Written entirely in Kotlin, leveraging its powerful features for Android development.


## Screenshots

<div style="display: flex; justify-content: center; align-items: center;">
    <img src="https://github.com/Bhavyansh03-tech/BottomBar/assets/96388594/f708a1ac-f329-44c5-ad0d-b3d92846fefc" alt="First Screenshot" style="width: 200px; height: auto; margin-right: 10px;">
    <img src="https://github.com/Bhavyansh03-tech/BottomBar/assets/96388594/d0e55ab5-6faa-4a96-b9e3-d03ea90abdfd" alt="Second Screenshot" style="width: 200px; height: auto;">
</div>


## Getting Started

### Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/Bhavyansh03-tech/BottomBar.git
    ```
2. **Open the project in Android Studio**:
    - File > Open > Select the cloned project directory.

3. **Build the project**:
    - Ensure that the necessary dependencies are downloaded and configured by building the project via Build > Make Project.

### Usage

1. **Setup Bottom Navigation**:
    - Define the navigation items and their corresponding screens.

    ```kotlin
    data class BottomNavItems(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int
    )
    
    val bottomNavItemList = listOf(
        BottomNavItems(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
            badgeCount = 0
        ),
        BottomNavItems(
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            hasNews = false,
            badgeCount = 0
        ),
        BottomNavItems(
            title = "Notifications",
            selectedIcon = Icons.Filled.Notifications,
            unselectedIcon = Icons.Outlined.Notifications,
            hasNews = false,
            badgeCount = 15
        ),
        BottomNavItems(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = true,
            badgeCount = 0
        )
    
    )
    ```

    - Implement the bottom navigation bar in your main composable function.

    ```kotlin
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
    ```

2. **Implement Horizontal Pager**:
    - Use `HorizontalPager` from Accompanist library to create swipeable screens.

    ```kotlin
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
    ```

3. **Combining Navigation and Pager**:
    - Sync the bottom navigation with the horizontal pager to provide a cohesive user experience.

    ```kotlin
    @Composable
    fun BottomBar(
        navController: NavHostController,
        pagerState: PagerState
    ) {
    
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val coroutineScope = rememberCoroutineScope()
    
        NavigationBar {
    
            bottomNavItemList.forEachIndexed { index, item ->
    
                val isSelected = currentRoute == item.title.lowercase()
    
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index) // Scroll pager to the selected page without animation
                        }
                        navController.navigate(item.title.lowercase()) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount > 0) {
                                    Text(text = item.badgeCount.toString())
                                } else if (item.hasNews) {
                                    Badge()
                                }
                            }) {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    },
                    label = {
                        Text(text = item.title)
                    }
                )
            }
        }
    
    }
    ```

### Project Structure

- `MainActivity.kt`: The main entry point of the app.
- `navigation/`: Contains navigation logic using Jetpack Compose.
- `ui/`: Contains the composable functions and UI components for different screens.

## Contributing

Contributions are welcome! Please fork the repository and use a feature branch. Pull requests are warmly welcome.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a pull request

## Acknowledgements

- Inspiration from various Android development tutorials and documentation.

## Contact

For questions or feedback, please contact [@Bhavyansh03-tech](https://github.com/Bhavyansh03-tech) on GitHub or connect with me on [LinkedIn](https://www.linkedin.com/in/bhavyansh03/).

---

