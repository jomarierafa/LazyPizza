package com.jvrcoding.lazypizza.app.navigation

import com.jvrcoding.lazypizza.product.presentation.model.Tab
import kotlinx.serialization.Serializable

sealed interface NavigationRoute {

    @Serializable
    data object Authentication

    @Serializable
    data object Product

    @Serializable
    data class Main(
        val tab: Tab,
    ): NavigationRoute

    @Serializable
    data class ProductDetails(
        val productId: String,
        val productImage: String,
        val productPrice: String,
        val productName: String,
        val productDescription: String,
    ) : NavigationRoute

    @Serializable
    data object Checkout
}