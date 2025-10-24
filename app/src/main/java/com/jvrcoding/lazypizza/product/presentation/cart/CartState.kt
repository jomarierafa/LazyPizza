package com.jvrcoding.lazypizza.product.presentation.cart

import com.jvrcoding.lazypizza.product.presentation.cart.model.CartProductUi

data class CartState(
    val products: List<CartProductUi> = emptyList(),
)
