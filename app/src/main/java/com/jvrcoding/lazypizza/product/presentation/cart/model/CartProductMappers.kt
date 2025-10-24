package com.jvrcoding.lazypizza.product.presentation.cart.model

import com.jvrcoding.lazypizza.product.domain.cart.CartProduct

fun CartProduct.toCartProductUi(): CartProductUi {
    return CartProductUi(
        id = uid ?: 0,
        imageUrl = imageUrl,
        name = name,
        description = productToppings.joinToString(", ") { "${it.quantity}x ${it.name}" },
        quantity = quantity,
        totalPrice = "$totalPrice"
    )
}