package com.jvrcoding.lazypizza.core.presentation.designsystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.primaryGradient
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textOnPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title3

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressedGradient = Brush.linearGradient(
        listOf(Color(0xFFE57A56), Color(0xFFD14E34))
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .padding(bottom = 6.dp)
            .then(
                if (!isPressed && enabled) {
                    Modifier.shadow(
                        elevation = 6.dp,
                        shape = CircleShape,
                        ambientColor = MaterialTheme.colorScheme.primary,
                        spotColor = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Modifier
                }
            )
            .background(
                brush = if (!enabled) {
                    SolidColor(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                } else {
                    if (isPressed)
                        pressedGradient
                    else
                        MaterialTheme.colorScheme.primaryGradient
                },
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            )
            .padding(horizontal = 24.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.title3,
            color = if (enabled)
                MaterialTheme.colorScheme.textOnPrimary
            else
                MaterialTheme.colorScheme.textPrimary.copy(alpha = 0.4f)
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrimaryButtonPreview() {
    LazyPizzaTheme {
        PrimaryButton(
            enabled = true,
            text = "Label",
            onClick = {},
            modifier = Modifier.fillMaxWidth(0.8f)
        )

    }
}
