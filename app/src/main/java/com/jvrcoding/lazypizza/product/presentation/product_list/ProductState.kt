package com.jvrcoding.lazypizza.product.presentation.product_list

import com.jvrcoding.lazypizza.core.presentation.util.UiText
import com.jvrcoding.lazypizza.product.presentation.product_list.models.ProductSection
import com.jvrcoding.lazypizza.product.presentation.product_list.models.ProductUi
import com.jvrcoding.lazypizza.product.presentation.product_list.models.SelectedProduct

data class ProductState(
    val searchQuery: String = "",
    val selectedProducts: List<SelectedProduct> = emptyList(),
    val products: Map<UiText, List<ProductUi>> = emptyMap(),
) {
    val productSections = products
        .toList()
        .map { (category, products) ->
            ProductSection(category, products)
        }
    val wow = products.values.flatten()
}