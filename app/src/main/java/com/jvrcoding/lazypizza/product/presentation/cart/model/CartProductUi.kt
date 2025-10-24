package com.jvrcoding.lazypizza.product.presentation.cart.model

data class CartProductUi(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val description: String,
    val quantity: Int,
    val totalPrice: String,
)
