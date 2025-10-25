package com.jvrcoding.lazypizza.product.presentation.cart

import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi

sealed interface CartAction {
    data class OnRemoveItem(val productUid: Int): CartAction
    data class OnAddProduct(val productUi: RecommendedProductUi): CartAction
}