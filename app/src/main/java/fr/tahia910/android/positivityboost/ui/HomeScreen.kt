package fr.tahia910.android.positivityboost.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.model.AnimalItem
import fr.tahia910.android.positivityboost.model.Result
import fr.tahia910.android.positivityboost.model.Status
import fr.tahia910.android.positivityboost.ui.component.Drawer
import fr.tahia910.android.positivityboost.ui.component.LoadingImageAnimation
import fr.tahia910.android.positivityboost.ui.theme.PositivityBoostTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(quote: Result<String>?, animalImage: Result<AnimalItem>?, onNext: () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    IconButton(
                        onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu, contentDescription = stringResource(
                                id = R.string.content_description_open_drawer
                            )
                        )
                    }
                }
            )
        },
        drawerContent = {
            Drawer(onHomeSelected = {}, onSettingsSelected = {})
        }
    ) { innerPadding ->
        HomeContentBody(
            quote = quote,
            animalImage = animalImage,
            onNext = onNext,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeContentBody(
    modifier: Modifier = Modifier,
    quote: Result<String>?,
    animalImage: Result<AnimalItem>?,
    onNext: () -> Unit
) {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        if (quote?.status == Status.SUCCESS && animalImage?.status == Status.SUCCESS) {
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
                        style = MaterialTheme.typography.h1,
                        textAlign = TextAlign.Center
                    )
                }
                item {
                    AnimalImage(animalImage.data)
                }
            }
        }

        val (buttonRow, errorMessage) = createRefs()

        ErrorMessage(
            quoteStatus = quote?.status,
            imageStatus = animalImage?.status,
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
private fun AnimalImage(animalImage: AnimalItem?) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(bottom = 64.dp) // account for the bottom button
            .clearAndSetSemantics { }, // ignore the image
        shape = MaterialTheme.shapes.medium
    ) {
        GlideImage(
            imageModel = { animalImage?.url ?: "" }, // loading a network image using an URL.
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillWidth
            ),
            modifier = Modifier.fillMaxWidth(),
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
fun ErrorMessage(quoteStatus: Status?, imageStatus: Status?, modifier: Modifier = Modifier) {
    Box(modifier, Alignment.Center) {
        if (quoteStatus == Status.ERROR && imageStatus == Status.ERROR) {
            Text(
                text = stringResource(id = R.string.error_message_both),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else if (quoteStatus == Status.ERROR) {
            Text(
                text = stringResource(id = R.string.error_message_quote),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    PositivityBoostTheme {
        val animal = Result.success(AnimalItem("abc", "", 300, 300))
        HomeScreen(quote = Result.success("You got this"), animalImage = animal, onNext = {})
    }
}

@Preview
@Composable
fun DarkHomeScreenPreview() {
    PositivityBoostTheme(darkTheme = true) {
        val animal = Result.success(AnimalItem("abc", "", 300, 300))
        HomeScreen(quote = Result.success("You got this"), animalImage = animal, onNext = {})
    }
}
