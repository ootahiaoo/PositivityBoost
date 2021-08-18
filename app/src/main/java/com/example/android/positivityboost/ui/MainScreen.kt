package com.example.android.positivityboost.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.positivityboost.ui.theme.PositivityBoostTheme

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
fun ContentBody(
    modifier: Modifier = Modifier,
    quote: String? = null,
    onNextQuote: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        quote?.let {
            Text(
                text = it,
                modifier = Modifier.animateContentSize(
                    animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
                ),
                textAlign = TextAlign.Center
            )
        }

        // TODO: add loading animation for quote?

        val buttonTopPadding = if (quote == null) 42.dp else 16.dp
        Button(
            onClick = onNextQuote,
            modifier = Modifier.padding(top = buttonTopPadding),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            )
        ) {
            Text("Next")
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    PositivityBoostTheme {
        MainScreen(quote = "You got this", onNextQuote = {})
    }
}

@Preview
@Composable
fun DarkMainScreenPreview() {
    PositivityBoostTheme(darkTheme = true) {
        MainScreen(quote = "You got this", onNextQuote = {})
    }
}