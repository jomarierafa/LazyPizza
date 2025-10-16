package com.jvrcoding.lazypizza.product.presentation.product_details


sealed interface ProductDetailsAction {
    data object OnBackClick: ProductDetailsAction
    data class OnToppingSelect(val toppingId: String) : ProductDetailsAction
    data class OnAddQuantity(val toppingId: String) : ProductDetailsAction
    data class OnReduceQuantity(val toppingId: String) : ProductDetailsAction
}