package com.jvrcoding.lazypizza.app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {

    @Serializable
    data object Product

    @Serializable
    data class ProductDetails(
        val productId: String,
        val productImage: String,
        val productName: String,
        val productDescription: String,
    ) : NavigationRoute
}