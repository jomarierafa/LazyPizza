package com.jvrcoding.lazypizza.product.presentation.product_details

import com.jvrcoding.lazypizza.product.presentation.product_details.models.SelectedTopping
import com.jvrcoding.lazypizza.product.presentation.product_details.models.ToppingUi

data class ProductDetailsState(
    val imageUrl: String = "",
    val productName: String = "",
    val description: String = "",
    val toppings:  List<ToppingUi> = emptyList(),
    val selectedToppings: List<SelectedTopping> = emptyList(),
)
