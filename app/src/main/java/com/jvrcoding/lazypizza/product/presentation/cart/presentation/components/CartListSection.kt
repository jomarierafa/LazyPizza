package com.jvrcoding.lazypizza.product.presentation.cart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.product.presentation.components.ProductCard

@Composable
fun CartListSection(
//    cartList: List<CartItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(4) {
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
                selected = true
            )
        }
    }
}

@Preview
@Composable
private fun CartListSectionPreview() {
    LazyPizzaTheme {
        CartListSection()
    }
}