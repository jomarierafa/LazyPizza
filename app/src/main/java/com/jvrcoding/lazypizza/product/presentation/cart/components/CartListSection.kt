package com.jvrcoding.lazypizza.product.presentation.cart.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.product.presentation.cart.CartAction
import com.jvrcoding.lazypizza.product.presentation.cart.model.CartProductUi
import com.jvrcoding.lazypizza.product.presentation.components.ProductCard

fun LazyListScope.cartListContent(
    products: List<CartProductUi>,
    onAction: (CartAction) -> Unit,
) {
    items(
        products,
        key = { it -> it.id }
    ) {product ->
        ProductCard(
            imageUrl = product.imageUrl,
            productName = product.name,
            productPrice = product.totalPrice,
            description = product.description,
            quantity = product.quantity.toString(),
            imageSize = 106.dp,
            onAddClick = {
            },
            onMinusClick = {
            },
            onRemoveClick = {
                onAction(CartAction.OnRemoveItem(product.id))
            },
            selected = true,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .animateItem()
        )
    }
}

@Preview
@Composable
private fun CartListContentPreview() {
    LazyPizzaTheme {
        LazyColumn {
            cartListContent(
                products = emptyList(),
                onAction = {}
            )
        }
    }
}