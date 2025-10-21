package com.jvrcoding.lazypizza.product.presentation.menu.models

data class SelectedProduct(
    val productId: String,
    val quantity: Int,
    val totalPrice: String,
)
