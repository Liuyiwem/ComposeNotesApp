package com.yiwenliu.composenotesapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color.White,
    background = Blue,
    onBackground = Color.White,
    surface = LightBlue,
    onSurface = DarkGray,
)

@Composable
fun ComposeNotesAppTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorPalette,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}