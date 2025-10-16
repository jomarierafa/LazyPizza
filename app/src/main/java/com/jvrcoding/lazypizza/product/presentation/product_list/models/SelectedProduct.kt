package com.jvrcoding.lazypizza.product.presentation.product_list.models

data class SelectedProduct(
    val productId: String,
    val quantity: Int,
    val totalPrice: String,
)
