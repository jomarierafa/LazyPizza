package com.jvrcoding.lazypizza.product.presentation.util

import com.jvrcoding.lazypizza.app.navigation.NavigationRoute
import com.jvrcoding.lazypizza.product.domain.Product

fun Product.toProductDetailsRoute(): NavigationRoute.ProductDetails {
    return NavigationRoute.ProductDetails(
        productId = id,
        productImage = imageUrl,
        productName = name,
        productDescription = description
    )
}