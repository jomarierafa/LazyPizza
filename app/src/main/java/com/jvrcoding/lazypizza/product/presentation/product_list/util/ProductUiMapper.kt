package com.jvrcoding.lazypizza.product.presentation.product_list.util

import com.jvrcoding.lazypizza.product.domain.Product
import com.jvrcoding.lazypizza.product.presentation.product_list.models.ProductUi

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