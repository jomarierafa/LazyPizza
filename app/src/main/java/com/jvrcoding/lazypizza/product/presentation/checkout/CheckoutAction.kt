package com.jvrcoding.lazypizza.product.presentation.checkout

import com.jvrcoding.lazypizza.product.presentation.checkout.models.PickupTime

sealed interface CheckoutAction {
    data object OnBackClick: CheckoutAction
    data class OnPickupTimeSelected(val option: PickupTime) : CheckoutAction
    data class OnDateSelected(val date: Long) : CheckoutAction
    data object OnTimeSelected : CheckoutAction

    data object OnDismissDatePicker: CheckoutAction
    data object OnDismissTimePicker: CheckoutAction
}