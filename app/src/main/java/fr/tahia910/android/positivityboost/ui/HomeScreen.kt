package fr.tahia910.android.positivityboost.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.model.AnimalItem
import fr.tahia910.android.positivityboost.ui.component.LoadingAnimation
import fr.tahia910.android.positivityboost.ui.theme.PositivityBoostTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val homeUiState by viewModel.quoteUiState.collectAsStateWithLifecycle()
    val animalImageUiState by viewModel.animalImageUiState.collectAsStateWithLifecycle()

    HomeContentBody(
        quoteUiState = homeUiState,
        animalImageUiState = animalImageUiState,
        onNext = { viewModel.refresh() }
    )
}

@Composable
fun HomeContentBody(
    quoteUiState: QuoteUiState,
    animalImageUiState: AnimalImageUiState,
    onNext: () -> Unit
) {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        if (quoteUiState is QuoteUiState.Success && animalImageUiState is AnimalImageUiState.Success) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = quoteUiState.quote ?: "",
                        modifier = Modifier
                            .padding(start = 16.dp, top = 34.dp, end = 16.dp, bottom = 34.dp)
                            .animateContentSize(),
                        style = MaterialTheme.typography.h1,
                        textAlign = TextAlign.Center
                    )
                }
                item {
                    AnimalImage(animalImageUiState.image)
                }
            }
        }

        val (buttonRow, errorMessage) = createRefs()

        ErrorMessage(
            isQuoteError = quoteUiState == QuoteUiState.Error,
            isAnimalImageError = animalImageUiState == AnimalImageUiState.Error,
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
                LoadingAnimation(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 44.dp)
                )
            },
            failure = {
                Text(
                    text = stringResource(id = R.string.error_message_photo),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
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
fun ErrorMessage(
    isQuoteError: Boolean,
    isAnimalImageError: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxWidth(), Alignment.Center) {
        if (isQuoteError && isAnimalImageError) {
            Text(
                text = stringResource(id = R.string.error_message_both),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else if (isQuoteError) {
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
        val quoteUiState = QuoteUiState.Success("You got this")
        val animalItem = AnimalItem("abc", "", 300, 300)
        val animalImageUiState = AnimalImageUiState.Success(animalItem)
        HomeContentBody(
            quoteUiState = quoteUiState,
            animalImageUiState = animalImageUiState,
            onNext = {}
        )
    }
}

@Preview
@Composable
fun DarkHomeScreenPreview() {
    PositivityBoostTheme(darkTheme = true) {
        val quoteUiState = QuoteUiState.Success(quote = "You got this")
        val animalItem = AnimalItem("abc", "", 300, 300)
        val animalImageUiState = AnimalImageUiState.Success(animalItem)
        HomeContentBody(
            quoteUiState = quoteUiState,
            animalImageUiState = animalImageUiState,
            onNext = {}
        )
    }
}
