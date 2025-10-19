package com.jvrcoding.lazypizza.main.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface MainNavigationRoute {
    @Serializable
    data object Menu
    @Serializable
    data object Cart
    @Serializable
    data object History
}