package com.jvrcoding.lazypizza.product.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryIconButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.MinusIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.PlusIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title2

@Composable
fun QuantitySelector(
    quantity: String,
    onMinusClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
    ) {
        PrimaryIconButton(
            icon = MinusIcon,
            iconTint = MaterialTheme.colorScheme.textSecondary,
            onClick = {},
            modifier = Modifier.size(22.dp)
        )

        Text(
            text = quantity,
            style = MaterialTheme.typography.title2,
            color = MaterialTheme.colorScheme.textPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        PrimaryIconButton(
            icon = PlusIcon,
            iconTint = MaterialTheme.colorScheme.textSecondary,
            onClick = {},
            modifier = Modifier.size(22.dp)
        )
    }
}

@Preview
@Composable
private fun QuantitySelectorPreview() {
    LazyPizzaTheme {
        QuantitySelector(
            quantity = "1",
            onMinusClick = {},
            onAddClick = {}
        )
    }
}