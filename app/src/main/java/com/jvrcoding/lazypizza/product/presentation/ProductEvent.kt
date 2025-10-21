package com.jvrcoding.lazypizza.product.presentation

interface ProductEvent {
    data object NavigateToMenu: ProductEvent
    data object NavigateToCart: ProductEvent
    data object NavigateToHistory: ProductEvent
}