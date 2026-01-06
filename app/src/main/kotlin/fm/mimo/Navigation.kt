package fm.mimo

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Done : Screen("done")
}