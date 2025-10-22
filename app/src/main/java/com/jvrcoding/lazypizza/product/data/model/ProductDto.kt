package com.jvrcoding.lazypizza.product.data.model

data class ProductDto(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val imageUrl: String = "",
    val type: String = ""
)