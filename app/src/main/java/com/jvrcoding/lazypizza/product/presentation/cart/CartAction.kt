package com.jvrcoding.lazypizza.product.presentation.cart

import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi

sealed interface CartAction {
    data class OnRemoveItem(val productUid: Int): CartAction
    data class OnAddProduct(val productUi: RecommendedProductUi): CartAction
    data class OnIncreaseQuantity(val productUid: Int): CartAction
    data class OnDecreaseQuantity(val productUid: Int): CartAction
    data object OnBackToMenuClick: CartAction
    data object OnProceedToPlaceOrderClick: CartAction
}