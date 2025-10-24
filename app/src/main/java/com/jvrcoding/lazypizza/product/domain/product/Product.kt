package com.jvrcoding.lazypizza.product.domain.product

import java.math.BigDecimal

data class Product(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val description: String,
    val imageUrl: String,
    val type: String
)