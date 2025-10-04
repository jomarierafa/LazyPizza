package com.jvrcoding.lazypizza.core.presentation.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.Primary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.primary8
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title3


@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }

    CompositionLocalProvider(
        LocalRippleConfiguration provides RippleConfiguration(color = Primary.copy(alpha = 0.08f))
    ) {
        OutlinedButton(
            interactionSource = interactionSource,
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            border = BorderStroke(
                width = 1.dp,
                color = if(!enabled) {
                    MaterialTheme.colorScheme.outline
                } else {
                    MaterialTheme.colorScheme.primary8
                }
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.title3,
                color = if (enabled)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.textPrimary.copy(alpha = 0.4f)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SecondaryButtonPreview() {
    LazyPizzaTheme {
        SecondaryButton(
            enabled = false,
            text = "Label",
            onClick = {},
            modifier = Modifier.fillMaxWidth(0.8f)
        )

    }
}