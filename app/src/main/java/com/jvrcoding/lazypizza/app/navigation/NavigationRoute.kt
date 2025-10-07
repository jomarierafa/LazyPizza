package com.jvrcoding.lazypizza.app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {

    @Serializable
    data object Product
}