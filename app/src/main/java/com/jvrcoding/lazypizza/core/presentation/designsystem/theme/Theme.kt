package com.jvrcoding.lazypizza.core.presentation.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

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