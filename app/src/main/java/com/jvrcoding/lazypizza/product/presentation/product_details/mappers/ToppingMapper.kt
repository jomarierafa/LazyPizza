package com.jvrcoding.lazypizza.product.presentation.product_details.mappers

import com.jvrcoding.lazypizza.product.domain.product.Topping
import com.jvrcoding.lazypizza.product.presentation.product_details.models.ToppingUi

fun Topping.toToppingUi(): ToppingUi {
    return ToppingUi(
        id = id,
        name = name,
        price = "$$price",
        imageUrl = imageUrl,
    )
}