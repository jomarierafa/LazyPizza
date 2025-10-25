package com.jvrcoding.lazypizza.product.presentation.cart

import com.jvrcoding.lazypizza.product.presentation.cart.model.CartProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi

data class CartState(
    val products: List<CartProductUi> = emptyList(),
    val recommendedProducts: List<RecommendedProductUi> = emptyList()
)
