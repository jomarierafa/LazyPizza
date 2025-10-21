package com.jvrcoding.lazypizza.product.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ProductNavigationRoute {
    @Serializable
    data object Menu
    @Serializable
    data object Cart
    @Serializable
    data object History
}