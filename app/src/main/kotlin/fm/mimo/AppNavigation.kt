package fm.mimo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fm.mimo.Screen.Done
import fm.mimo.ui.screens.done.DoneScreen
import fm.mimo.ui.screens.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Home screen destination
        composable(Screen.Home.route) {
            HomeScreen {
                navController.navigate(Done.route)
            }
        }

        // Lesson Done destination
        composable(route = Done.route) {
            DoneScreen()
        }
    }
}
