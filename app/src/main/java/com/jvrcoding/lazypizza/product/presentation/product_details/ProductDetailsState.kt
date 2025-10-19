package com.jvrcoding.lazypizza.product.presentation.product_details

import com.jvrcoding.lazypizza.product.presentation.product_details.models.SelectedTopping
import com.jvrcoding.lazypizza.product.presentation.product_details.models.ToppingUi
import java.math.BigDecimal

data class ProductDetailsState(
    val imageUrl: String = "",
    val productName: String = "",
    val description: String = "",
    val basePrice: BigDecimal = BigDecimal.ZERO,
    val toppings:  List<ToppingUi> = emptyList(),
    val selectedToppings: List<SelectedTopping> = emptyList(),
) {
    val totalPrice: BigDecimal = basePrice + selectedToppings.sumOf { it.price * it.quantity.toBigDecimal()}
}