package com.jvrcoding.lazypizza.product.presentation.order_history

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar.SecondaryToolbar
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.product.presentation.components.EmptyStateScreen

@Composable
fun OrderHistoryScreenRoot() {
    OrderHistoryScreen()
}

@Composable
fun OrderHistoryScreen() {
    Scaffold(
        topBar = {
            SecondaryToolbar(
                title = stringResource(R.string.order_history),
                showBackButton = false
            )
        }
    ) { innerPadding ->
        EmptyStateScreen(
            modifier = Modifier.padding(innerPadding),
            title = stringResource(R.string.not_signed_in),
            subtitle = stringResource(R.string.please_sign_in_to_view_your_order_history),
            buttonText = stringResource(R.string.sign_in),
            onButtonClick = {}
        )
    }
}

@Preview
@Composable
private fun OrderHistoryScreenPreview() {
    LazyPizzaTheme {
        OrderHistoryScreen()
    }
}