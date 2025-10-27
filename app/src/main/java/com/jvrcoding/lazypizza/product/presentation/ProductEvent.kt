package com.jvrcoding.lazypizza.product.presentation

sealed interface ProductEvent {
    data object NavigateToMenu: ProductEvent
    data object NavigateToCart: ProductEvent
    data object NavigateToHistory: ProductEvent
}