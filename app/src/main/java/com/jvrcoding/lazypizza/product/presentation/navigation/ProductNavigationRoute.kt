package com.jvrcoding.lazypizza.product.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ProductNavigationRoute {
    @Serializable
    data object Menu: ProductNavigationRoute
    @Serializable
    data object Cart: ProductNavigationRoute
    @Serializable
    data object History: ProductNavigationRoute
}