package fr.tahia910.android.positivityboost.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.tahia910.android.positivityboost.navigation.Destination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
): AppState {
    return remember(navController, coroutineScope, scaffoldState) {
        AppState(navController, coroutineScope, scaffoldState)
    }
}

class AppState(
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
    val scaffoldState: ScaffoldState
) {
    private val currentNavDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentDestination: Destination?
        @Composable get() = when (currentNavDestination?.route) {
            Destination.SETTINGS.route -> Destination.SETTINGS
            Destination.HOME.route -> Destination.HOME
            else -> null
        }

    fun navigateTo(destination: Destination) {
        navController.navigate(destination.route)
        coroutineScope.launch { scaffoldState.drawerState.close() }
    }

    fun openDrawer() {
        coroutineScope.launch { scaffoldState.drawerState.open() }
    }
}
