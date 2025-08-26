package com.example.koindi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.koindi.data.model.BottomNavItem
import com.example.koindi.ui.screen.BottomNavigationBar
import com.example.koindi.ui.screen.Navigation
import com.example.koindi.ui.screen.Screen
import com.example.koindi.ui.theme.KoinDiTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinDiTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination?.route

                val showBottomBar = currentDestination != Screen.SplashScreen.route
                val items = listOf(
                    BottomNavItem(
                        label = "Home",
                        icon = Icons.Default.Home,
                        route = Screen.HomeScreen.route
                    ),
                    BottomNavItem(
                        label = "Movies",
                        icon = Icons.Default.Favorite,
                        route = Screen.MovieListScreen.route
                    ),
                    BottomNavItem(
                        label = "Animation",
                        icon = Icons.Default.Favorite,
                        route = Screen.SimpleAnimation.route
                    ),
                    BottomNavItem(
                        label = "Greet",
                        icon = Icons.Default.Favorite,
                        route = Screen.TextInputScreen.route
                    ),
                )

                Scaffold(
                    bottomBar =  {
                        if (showBottomBar) {
                            BottomNavigationBar(
                                items = items,
                                navController = navController,
                                onItemClick = { item ->
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { paddingValue ->
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize()
                            .padding(paddingValue),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Navigation(navController = navController)
//                    Greeting("Android")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KoinDiTheme {
        Greeting("Android")
    }
}