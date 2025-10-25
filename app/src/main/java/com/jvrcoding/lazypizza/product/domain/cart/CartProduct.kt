package com.jvrcoding.lazypizza.product.domain.cart

import java.math.BigDecimal
import java.time.Instant

data class CartProduct(
    val uid: Int? = null,
    val productId: String,
    val name: String,
    val totalPrice: BigDecimal,
    val description: String,
    val imageUrl: String,
    val quantity: Int,
    val productToppings: List<ProductTopping> = emptyList(),
    val createdAt: Instant
)

data class ProductTopping(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: BigDecimal,
    val productId: Int? = null,
)
