package com.jvrcoding.lazypizza.app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationGraph {
    @Serializable
    object AuthGraph

    @Serializable
    object ProductGraph
}