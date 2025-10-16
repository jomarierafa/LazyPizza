package com.jvrcoding.lazypizza.product.presentation.product_details.util

import com.jvrcoding.lazypizza.product.domain.Topping
import com.jvrcoding.lazypizza.product.presentation.product_details.models.ToppingUi

fun Topping.toToppingUi(): ToppingUi {
    return ToppingUi(
        id = id,
        name = name,
        price = "$$price",
        imageUrl = imageUrl,
    )
}