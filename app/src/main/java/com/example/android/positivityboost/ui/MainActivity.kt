package com.example.android.positivityboost.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainSreen(viewModel)
                }
            }
        }
    }
}


@Composable
fun MainSreen(viewModel: MainViewModel) {
    val quoteItem: QuoteItem? by viewModel.quoteItem.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Posivity Boost") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Settings, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        ContentBody(
            quote = quoteItem,
            nextQuote = { viewModel.getQuote() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ContentBody(quote: QuoteItem?, nextQuote: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        quote?.let {
            Text(it.affirmation)
        }

        Button(onClick = nextQuote, modifier = Modifier.padding(top = 8.dp)) {
            Text("Next")
        }
    }
}