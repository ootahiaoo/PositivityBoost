package fr.tahia910.android.positivityboost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.tahia910.android.positivityboost.ui.PositivityBoostApp
import fr.tahia910.android.positivityboost.ui.theme.PositivityBoostTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PositivityBoostTheme {
                PositivityBoostApp()
            }
        }
    }
}
