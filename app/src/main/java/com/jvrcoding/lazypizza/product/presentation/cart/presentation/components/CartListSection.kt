package com.jvrcoding.lazypizza.product.presentation.cart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.product.presentation.components.ProductCard

fun LazyListScope.cartListContent() {
    items(20) {
        ProductCard(
            imageUrl = "",
            productName = "Margherita",
            productPrice = "$3.39",
            description = "",
            quantity = "1",
            imageSize = 106.dp,
            onAddClick = {
            },
            onMinusClick = {
            },
            onRemoveClick = {
            },
            selected = true,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
private fun CartListContentPreview() {
    LazyPizzaTheme {
        LazyColumn {
            cartListContent()
        }
    }
}