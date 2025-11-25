package com.jvrcoding.lazypizza.product.presentation.checkout

sealed interface CheckoutAction {
    data object OnBackClick: CheckoutAction
}