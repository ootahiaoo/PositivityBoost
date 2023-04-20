package fr.tahia910.android.positivityboost.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = LightAmber,
    primaryVariant = DarkAmber,
    secondary = Blue
)

private val LightColorPalette = lightColors(
    primary = Amber,
    primaryVariant = DarkAmber,
    secondary = Blue,
    onPrimary = Color.Black
)

@Composable
fun PositivityBoostTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
