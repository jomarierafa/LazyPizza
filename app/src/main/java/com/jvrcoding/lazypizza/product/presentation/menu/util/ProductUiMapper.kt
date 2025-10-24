package com.jvrcoding.lazypizza.product.presentation.menu.util

import com.jvrcoding.lazypizza.product.domain.product.Product
import com.jvrcoding.lazypizza.product.presentation.menu.models.ProductUi

fun Product.toProductUi(): ProductUi {
    return ProductUi(
        id = id,
        name = name,
        price = "$$price",
        description = description,
        imageUrl = imageUrl,
        type = type
    )
}

fun ProductUi.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        price = price.replace("$", "").toBigDecimal(),
        description = description,
        imageUrl = imageUrl,
        type = type
    )
}