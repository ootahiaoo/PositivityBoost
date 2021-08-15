package com.example.android.positivityboost.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.android.positivityboost.model.QuoteItem
import com.example.android.positivityboost.ui.theme.PositivityBoostTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getQuote()
        setContent {
            PositivityBoostTheme {
                MainActivityScreen(viewModel)
            }
        }
    }
}

@Composable
fun MainActivityScreen(viewModel: MainViewModel) {
    val quoteItem: QuoteItem? by viewModel.quoteItem.observeAsState()
    MainScreen(
        quote = quoteItem?.affirmation,
        onNextQuote = viewModel::getQuote
    )
}