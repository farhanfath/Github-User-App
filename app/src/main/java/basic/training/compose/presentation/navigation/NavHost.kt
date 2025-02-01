package basic.training.compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import basic.training.compose.presentation.ui.screens.DetailScreen
import basic.training.compose.presentation.ui.screens.HomeScreen

@Composable
fun NavHostApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(navController = navController)
        }
        composable<Profile> {

        }
        composable<Detail> { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            DetailScreen(username = username)
        }
    }
}