package fr.tahia910.android.positivityboost.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.ui.HomeScreen
import fr.tahia910.android.positivityboost.ui.SettingsScreen

enum class Destination(val route: String, @StringRes val screenTitle: Int) {
    HOME("home", R.string.app_name),
    SETTINGS("settings", R.string.drawer_settings)
}

@Composable
fun PositivityBoostNavHost(
    navController: NavHostController,
    startDestination: String = Destination.HOME.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Destination.HOME.route) {
            HomeScreen()
        }

        composable(route = Destination.SETTINGS.route) {
            SettingsScreen()
        }
    }
}
