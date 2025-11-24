package com.jvrcoding.lazypizza.product.presentation.checkout

import com.jvrcoding.lazypizza.product.presentation.cart.model.CartProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi

data class CheckoutState(
    val products: List<CartProductUi> = emptyList(),
    val recommendedProducts: List<RecommendedProductUi> = emptyList()
)