package fr.tahia910.android.positivityboost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.tahia910.android.positivityboost.ui.PositivityBoostApp
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
                PositivityBoostApp(viewModel)
            }
        }
    }
}
