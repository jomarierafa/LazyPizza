package com.jvrcoding.lazypizza.product.presentation.cart.model

import com.jvrcoding.lazypizza.product.domain.cart.CartProduct
import com.jvrcoding.lazypizza.product.domain.product.Product

fun CartProduct.toCartProductUi(): CartProductUi {
    return CartProductUi(
        id = uid ?: 0,
        productId = productId,
        imageUrl = imageUrl,
        name = name,
        description = productToppings.joinToString("\n") { "${it.quantity}x ${it.name}" },
        quantity = quantity,
        totalPrice = "$totalPrice"
    )
}

fun Product.toRecommendProductUi(): RecommendedProductUi {
    return RecommendedProductUi(
        id = id,
        imageUrl = imageUrl,
        name = name,
        price = "$price"
    )
}