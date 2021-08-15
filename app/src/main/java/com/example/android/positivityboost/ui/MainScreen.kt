package com.example.android.positivityboost.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(quote: String? = null, onNextQuote: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Posivity Boost") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { innerPadding ->
        ContentBody(
            quote = quote,
            onNextQuote = onNextQuote,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ContentBody(modifier: Modifier = Modifier, quote: String? = null, onNextQuote: () -> Unit) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        quote?.let {
            Text(quote)
        }
        Button(onClick = onNextQuote, modifier = Modifier.padding(top = 8.dp)) {
            Text("Next")
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(quote = "You got this", onNextQuote = {})
}