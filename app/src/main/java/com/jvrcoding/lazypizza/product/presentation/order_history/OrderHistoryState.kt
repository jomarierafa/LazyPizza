package com.jvrcoding.lazypizza.product.presentation.order_history

import com.jvrcoding.lazypizza.product.presentation.order_history.models.OrderDetailsUi

data class OrderHistoryState(
    val isUserSignedIn: Boolean = false,
    val fetchingOrders: Boolean = false,
    val orders: List<OrderDetailsUi> = emptyList()
)
