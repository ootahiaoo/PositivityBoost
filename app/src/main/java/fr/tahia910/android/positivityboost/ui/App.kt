package fr.tahia910.android.positivityboost.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.tahia910.android.positivityboost.navigation.Destination
import fr.tahia910.android.positivityboost.navigation.PositivityBoostNavHost
import fr.tahia910.android.positivityboost.ui.component.Drawer
import fr.tahia910.android.positivityboost.ui.component.TopAppBar
import fr.tahia910.android.positivityboost.ui.theme.PositivityBoostTheme

@Composable
fun PositivityBoostApp(appState: AppState = rememberAppState()) {
    PositivityBoostTheme {
        Scaffold(
            scaffoldState = appState.scaffoldState,
            topBar = {
                TopAppBar(appState = appState)
            },
            drawerContent = {
                Drawer(
                    onHomeSelected = {
                        appState.navigateTo(Destination.HOME)
                    },
                    onSettingsSelected = {
                        appState.navigateTo(Destination.SETTINGS)
                    }
                )
            }
        ) { innerPadding ->

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                PositivityBoostNavHost(navController = appState.navController)
            }
        }
    }
}
