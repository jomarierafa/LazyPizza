package com.jvrcoding.lazypizza.product.presentation.order_history.models

data class OrderDetailsUi(
    val orderId: String,
    val orderNumber: String,
    val orderCreated: String,
    val items: String,
    val status: String,
    val totalAmount: String
)