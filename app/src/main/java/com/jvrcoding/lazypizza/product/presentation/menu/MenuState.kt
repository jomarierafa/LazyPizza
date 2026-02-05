package com.jvrcoding.lazypizza.product.presentation.menu

import com.jvrcoding.lazypizza.core.presentation.util.UiText
import com.jvrcoding.lazypizza.product.presentation.menu.models.ProductSection
import com.jvrcoding.lazypizza.product.presentation.menu.models.ProductUi
import com.jvrcoding.lazypizza.product.presentation.menu.models.SelectedProduct

data class MenuState(
    val searchQuery: String = "",
    val selectedProducts: List<SelectedProduct> = emptyList(),
    val products: Map<UiText, List<ProductUi>> = emptyMap(),
    val fetchingProducts: Boolean = false,
    val isUserSignedIn: Boolean = false,
    val showConfirmationDialog: Boolean = false
) {
    val productSections = products
        .toList()
        .map { (category, products) ->
            ProductSection(category, products)
        }
}