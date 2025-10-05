package com.jvrcoding.lazypizza.core.presentation.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val TextPrimary = Color(0xFF03131F)
val TextSecondary = Color(0xFF627686)
val TextOnPrimary = Color(0xFFFFFFFF)

val BackGround = Color(0xFFFAFBFC)
val SurfaceHigher = Color(0xFFFFFFFF)
val SurfaceHighest = Color(0xFFF0F3F6)
val Outline = Color(0xFFE6E7ED)
val Primary = Color(0xFFF36B50)

val ColorScheme.primaryGradient: Brush
    get() = Brush.linearGradient(
        listOf(
            Color(0xFFF9966F),
            Color(0xFFF36B50)
        )
    )

val ColorScheme.textPrimary: Color
    get() = TextPrimary

val ColorScheme.textSecondary: Color
    get() = TextSecondary

val ColorScheme.textSecondary8: Color
    get() = TextSecondary.copy(alpha = 0.08f)

val ColorScheme.textOnPrimary: Color
    get() = TextOnPrimary

val ColorScheme.outLine50: Color
    get() = Outline.copy(alpha = 0.5f)

val ColorScheme.primary8: Color
    get() = Primary.copy(alpha = 0.08f)



