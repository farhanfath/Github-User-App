package basic.training.compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import basic.training.compose.presentation.ui.screens.DetailScreen
import basic.training.compose.presentation.ui.screens.HomeScreen

@Composable
fun NavHostApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                onDetailClick = { username, type, avatarUrl ->
                    navController.navigate(
                        Detail(
                            username = username,
                            type = type,
                            avatarUrl = avatarUrl
                        )
                    )
                }
            )
        }
        composable<Profile> {

        }
        composable<Detail> { entry ->
            val detail = entry.toRoute<Detail>()
            DetailScreen(
                username = detail.username,
                type = detail.type,
                avatarUrl = detail.avatarUrl
            )
        }
    }
}