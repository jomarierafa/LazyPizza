package com.jvrcoding.lazypizza.product.presentation.order_history

sealed interface OrderHistoryAction {
    data object OnSignInClick: OrderHistoryAction
    data object OnGotoMenuClick: OrderHistoryAction
}