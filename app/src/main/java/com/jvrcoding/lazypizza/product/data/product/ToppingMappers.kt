package com.jvrcoding.lazypizza.product.data.product

import com.jvrcoding.lazypizza.product.domain.product.Topping

fun ToppingDto.toTopping(): Topping {
    return Topping(
        id = id,
        name = name,
        imageUrl = imageUrl,
        price = price.toBigDecimal()
    )
}