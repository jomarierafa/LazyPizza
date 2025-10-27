package com.jvrcoding.lazypizza.product.presentation.product_details

sealed interface ProductDetailsEvent {
    data object NavigateToCartScreen: ProductDetailsEvent
}