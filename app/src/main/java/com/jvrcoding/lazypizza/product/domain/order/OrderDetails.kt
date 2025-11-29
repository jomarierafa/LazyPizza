package com.jvrcoding.lazypizza.product.domain.order

import com.jvrcoding.lazypizza.product.domain.cart.CartProduct
import java.math.BigDecimal
import java.time.Instant

data class OrderDetails(
    val id: String = "",
    val userId: String,
    val orderNumber: String,
    val pickupTime: Instant,
    val createdAt: Instant,
    val totalAmount: BigDecimal,
    val status: String,
    val comment: String,
    val products: List<CartProduct>
)
