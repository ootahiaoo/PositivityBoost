package fr.tahia910.android.positivityboost.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import fr.tahia910.android.positivityboost.R

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
