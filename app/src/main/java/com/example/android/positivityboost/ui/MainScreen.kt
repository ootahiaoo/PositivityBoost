package com.example.android.positivityboost.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.android.positivityboost.R
import com.example.android.positivityboost.model.DogItem
import com.example.android.positivityboost.model.Result
import com.example.android.positivityboost.model.Status
import com.example.android.positivityboost.ui.theme.LightAmber
import com.example.android.positivityboost.ui.theme.PositivityBoostTheme

@Composable
fun MainScreen(quote: Result<String>?, dogImage: Result<DogItem>?, onNext: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.settings)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ContentBody(
            quote = quote,
            dogImage = dogImage,
            onNext = onNext,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ContentBody(
    modifier: Modifier = Modifier,
    quote: Result<String>?,
    dogImage: Result<DogItem>?,
    onNext: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if ((quote?.status == Status.SUCCESS) && (dogImage?.status == Status.SUCCESS)) {
            item {
                Text(
                    text = quote.data ?: "",
                    modifier = Modifier
                        .padding(16.dp),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
            item {
                AnimalImage(dogImage.data!!)
            }
            item {
                Button(
                    onClick = onNext,
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    )
                ) {
                    Text(stringResource(id = R.string.next))
                }
            }
        }
    }
}


@ExperimentalCoilApi
@Composable
private fun AnimalImage(dogImage: DogItem) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )
    val painter = rememberImagePainter(
        data = dogImage.url,
        builder = {
            placeholder(R.drawable.ic_sun_primary)
        }
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        shape = MaterialTheme.shapes.medium
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.content_description_image),
            contentScale = if (painter.state is ImagePainter.State.Success) {
                ContentScale
                    .FillWidth
            } else {
                ContentScale.Inside
            },
            modifier = if (painter.state is ImagePainter.State.Success) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(LightAmber.copy(alpha = alpha))
            }
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    PositivityBoostTheme {
        val dog = Result.success(DogItem("abc", "", 300, 300))
        MainScreen(quote = Result.success("You got this"), dogImage = dog, onNext = {})
    }
}

@Preview
@Composable
fun DarkMainScreenPreview() {
    PositivityBoostTheme(darkTheme = true) {
        val dog = Result.success(DogItem("abc", "", 300, 300))
        MainScreen(quote = Result.success("You got this"), dogImage = dog, onNext = {})
    }
}