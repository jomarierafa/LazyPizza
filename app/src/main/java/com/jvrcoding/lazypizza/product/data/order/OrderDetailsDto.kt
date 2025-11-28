package com.jvrcoding.lazypizza.product.data.order

import com.google.firebase.Timestamp

data class OrderDetailsDto(
    val userId: String = "",
    val orderNumber: String = "",
    val pickupTime: Timestamp = Timestamp.now(),
    val createdAt: Timestamp = Timestamp.now(),
    val totalAmount: Double = 0.0,
    val comment: String = "",
    val status: String = "",
    val products: List<OrderProductDto> = emptyList()
)

data class OrderProductDto(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val productToppings: List<OrderProductToppingDto> = emptyList()
)

data class OrderProductToppingDto(
    val id: String = "",
    val name: String = "",
    val quantity: Int = 0,
    val price: Double = 0.0,
)