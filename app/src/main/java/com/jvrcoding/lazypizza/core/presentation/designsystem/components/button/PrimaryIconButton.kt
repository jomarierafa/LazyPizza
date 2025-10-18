package com.jvrcoding.lazypizza.core.presentation.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.MinusIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.outLine50
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary

@Composable
fun PrimaryIconButton(
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedIconButton(
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outLine50
        ),
        colors = IconButtonDefaults.outlinedIconButtonColors(
        ),
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if(enabled) iconTint else iconTint.copy(alpha = 0.25f),
            modifier = Modifier.size(14.dp)
        )
    }
}

@Preview
@Composable
private fun IconButtonPreview() {
    LazyPizzaTheme {
        PrimaryIconButton(
            enabled = true,
            icon = MinusIcon,
            iconTint = MaterialTheme.colorScheme.textSecondary,
            onClick = {}
        )
    }
}