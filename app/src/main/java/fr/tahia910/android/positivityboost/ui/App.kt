package fr.tahia910.android.positivityboost.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fr.tahia910.android.positivityboost.MainViewModel
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.navigation.Destination
import fr.tahia910.android.positivityboost.navigation.PositivityBoostNavHost
import fr.tahia910.android.positivityboost.ui.component.Drawer
import fr.tahia910.android.positivityboost.ui.theme.PositivityBoostTheme

// TODO: inject ViewModel directly into HomeScreen?
@Composable
fun PositivityBoostApp(
    viewModel: MainViewModel,
    appState: AppState = rememberAppState()
) {
    PositivityBoostTheme {
        Scaffold(
            scaffoldState = appState.scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        appState.currentDestination?.let { destination ->
                            Text(text = stringResource(id = destination.screenTitle))
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { appState.openDrawer() }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = stringResource(
                                    id = R.string.content_description_open_drawer
                                )
                            )
                        }
                    }
                )
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
                PositivityBoostNavHost(
                    navController = appState.navController,
                    mainViewModel = viewModel
                )
            }
        }
    }
}
