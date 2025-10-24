package com.jvrcoding.lazypizza.product.presentation.cart

sealed interface CartAction {
    data class OnRemoveItem(val productUid: Int): CartAction
}