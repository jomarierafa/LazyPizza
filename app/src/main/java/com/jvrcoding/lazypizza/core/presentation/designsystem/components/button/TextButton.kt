package com.jvrcoding.lazypizza.core.presentation.designsystem.components.button

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.Primary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title3

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    CompositionLocalProvider(
        LocalRippleConfiguration provides RippleConfiguration(color = Primary.copy(alpha = 0.08f))
    ) {
        TextButton(
            shape = CircleShape,
            onClick = onClick,
            enabled = enabled,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.title3,
                color = if(enabled)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.textPrimary.copy(alpha = 0.4f)
            )
        }
    }
}

@Preview
@Composable
private fun TextButtonPreview() {
    LazyPizzaTheme {
        TextButton(
            text = "Resend code",
            onClick = {},
            enabled = true
        )
    }
}