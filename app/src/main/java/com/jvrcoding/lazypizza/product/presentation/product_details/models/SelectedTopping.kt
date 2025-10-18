package com.jvrcoding.lazypizza.product.presentation.product_details.models

import java.math.BigDecimal

data class SelectedTopping(
    val id: String,
    val quantity: Int,
    val price: BigDecimal,
)
