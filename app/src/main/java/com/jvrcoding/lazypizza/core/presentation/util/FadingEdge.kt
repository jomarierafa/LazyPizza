package com.jvrcoding.lazypizza.core.presentation.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.fadingEdge(
    fadeHeight: Float = 100f,
    color: Color = Color.Black
) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()

        val height = size.height
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(color, Color.Transparent),
                startY = height - fadeHeight,
                endY = height
            ),
            blendMode = BlendMode.DstIn
        )
    }