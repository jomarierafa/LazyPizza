package com.jvrcoding.lazypizza.product.domain.product

import java.math.BigDecimal

data class Topping(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val imageUrl: String,
)