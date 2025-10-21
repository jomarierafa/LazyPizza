package com.jvrcoding.lazypizza.product.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body3Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title1Medium

@Composable
fun EmptyStateScreen(
    title:  String,
    subtitle: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.title1Medium,
            color = MaterialTheme.colorScheme.textPrimary
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.body3Regular,
            color = MaterialTheme.colorScheme.textSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryButton(
            text = buttonText,
            onClick = onButtonClick
        )
    }

}

@Preview
@Composable
private fun EmptyStateScreenPreview() {
    LazyPizzaTheme {
        EmptyStateScreen(
            title = "Not Signed In",
            subtitle = "Please sign in to view your order history.",
            buttonText = "Sign In",
            onButtonClick = {}

        )
    }
}