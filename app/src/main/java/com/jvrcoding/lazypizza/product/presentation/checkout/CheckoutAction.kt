package com.jvrcoding.lazypizza.product.presentation.checkout

import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi
import com.jvrcoding.lazypizza.product.presentation.checkout.models.PickupTime

sealed interface CheckoutAction {
    data object OnBackClick: CheckoutAction
    data object OnBackToMenuClick: CheckoutAction
    data class OnPickupTimeSelected(val option: PickupTime) : CheckoutAction
    data class OnDateSelected(val date: Long) : CheckoutAction
    data class TimeSelected(val hour: Int, val minute: Int) : CheckoutAction
    data class OnCommentChange(val comment: String) : CheckoutAction

    data object OnDismissDatePicker: CheckoutAction
    data object OnDismissTimePicker: CheckoutAction

    data class OnRemoveItem(val productUid: Int): CheckoutAction
    data class OnAddProduct(val productUi: RecommendedProductUi): CheckoutAction
    data class OnIncreaseQuantity(val productUid: Int): CheckoutAction
    data class OnDecreaseQuantity(val productUid: Int): CheckoutAction

    data object OnPlaceOrderClick: CheckoutAction
}