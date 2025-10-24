package com.jvrcoding.lazypizza.product.presentation.util

import com.jvrcoding.lazypizza.app.navigation.NavigationRoute
import com.jvrcoding.lazypizza.product.domain.product.Product

fun Product.toProductDetailsRoute(): NavigationRoute.ProductDetails {
    return NavigationRoute.ProductDetails(
        productId = id,
        productPrice = price.toString(),
        productImage = imageUrl,
        productName = name,
        productDescription = description
    )
}