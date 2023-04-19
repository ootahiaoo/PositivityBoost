package fr.tahia910.android.positivityboost.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.content.ContextCompat
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.ui.theme.PositivityBoostTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.refresh()
        }

        setContent {
            PositivityBoostTheme {
                MainActivityScreen(viewModel)
            }
        }
    }
}

@Composable
fun MainActivityScreen(viewModel: MainViewModel) {
    val quote by viewModel.quoteItem.observeAsState()
    val dogImage by viewModel.dogItem.observeAsState()

    MainScreen(
        quote = quote,
        dogImage = dogImage,
        onNext = {
            viewModel.refresh()
        }
    )
}
