package fr.tahia910.android.positivityboost.ui.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.ui.AppState

@Composable
fun TopAppBar(appState: AppState) {
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
}
