package com.jvrcoding.lazypizza.product.presentation.checkout

import com.jvrcoding.lazypizza.product.presentation.cart.model.CartProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi
import com.jvrcoding.lazypizza.product.presentation.checkout.models.PickupTime

data class CheckoutState(
    val selectedOption: PickupTime = PickupTime.EARLIEST,
    val products: List<CartProductUi> = emptyList(),
    val recommendedProducts: List<RecommendedProductUi> = emptyList(),
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
)