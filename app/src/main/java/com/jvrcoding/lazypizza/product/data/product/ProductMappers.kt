package com.jvrcoding.lazypizza.product.data.product

import com.jvrcoding.lazypizza.product.domain.product.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        price = price.toBigDecimal(),
        description = description,
        imageUrl = imageUrl,
        type = type
    )
}