package com.jvrcoding.lazypizza.core.presentation.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    surfaceContainerHigh = SurfaceHigher,
    surfaceContainerHighest = SurfaceHighest,
    outline = Outline,
    background = BackGround,
)

@Composable
fun LazyPizzaTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}