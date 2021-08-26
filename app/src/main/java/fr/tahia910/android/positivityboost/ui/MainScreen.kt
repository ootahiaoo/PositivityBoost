package fr.tahia910.android.positivityboost.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.glide.GlideImage
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.model.DogItem
import fr.tahia910.android.positivityboost.model.Result
import fr.tahia910.android.positivityboost.model.Status
import fr.tahia910.android.positivityboost.ui.theme.PositivityBoostTheme

@Composable
fun MainScreen(quote: Result<String>?, dogImage: Result<DogItem>?, onNext: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    // TODO: implement settings
//                    IconButton(onClick = { }) {
//                        Icon(
//                            imageVector = Icons.Filled.Settings,
//                            contentDescription = stringResource(R.string.settings)
//                        )
//                    }
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContentBody(
    modifier: Modifier = Modifier,
    quote: Result<String>?,
    dogImage: Result<DogItem>?,
    onNext: () -> Unit
) {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        if (quote?.status == Status.SUCCESS && dogImage?.status == Status.SUCCESS) {
            LazyColumn(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = quote.data ?: "",
                        modifier = Modifier
                            .padding(start = 16.dp, top = 34.dp, end = 16.dp, bottom = 34.dp)
                            .animateContentSize(),
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                }
                item {
                    AnimalImage(dogImage.data)
                }
            }
        }

        val (buttonRow, errorMessage) = createRefs()

        ErrorMessage(
            quoteStatus = quote?.status,
            dogStatus = dogImage?.status,
            modifier = Modifier
                .constrainAs(errorMessage) {
                    top.linkTo(parent.top, margin = 34.dp)
                }
                .fillMaxWidth()
        )

        BottomButton(
            onNext = onNext,
            modifier = Modifier.constrainAs(buttonRow) {
                bottom.linkTo(parent.bottom)
            }
        )
    }
}


@Composable
private fun AnimalImage(dogImage: DogItem?) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(bottom = 64.dp), // account for the bottom button
        shape = MaterialTheme.shapes.medium
    ) {
        GlideImage(
            imageModel = dogImage?.url ?: "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            contentDescription = stringResource(R.string.content_description_image),
            loading = {
                LoadingImageAnimation(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 44.dp)
                )
            },
            failure = {
                Text(stringResource(id = R.string.error_message_photo))
            }
        )
    }
}

@Composable
fun LoadingImageAnimation(modifier: Modifier = Modifier) {
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
    Box(modifier, Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_sun_primary),
            contentDescription = null,
            modifier = Modifier.alpha(alpha)
        )
    }
}

@Composable
fun BottomButton(onNext: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            )
        ) {
            Text(stringResource(id = R.string.next_action))
        }
    }
}

@Composable
fun ErrorMessage(quoteStatus: Status?, dogStatus: Status?, modifier: Modifier = Modifier) {
    Box(modifier, Alignment.Center) {
        if (quoteStatus == Status.ERROR && dogStatus == Status.ERROR) {
            Text(stringResource(id = R.string.error_message_both))
        } else if (quoteStatus == Status.ERROR) {
            Text(stringResource(id = R.string.error_message_quote))
        }
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