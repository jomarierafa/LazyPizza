package com.jvrcoding.lazypizza.product.presentation.product_list

import com.jvrcoding.lazypizza.core.presentation.util.UiText
import com.jvrcoding.lazypizza.product.presentation.product_list.models.ProductSection
import com.jvrcoding.lazypizza.product.presentation.product_list.models.ProductUi

data class ProductState(
    val products: Map<UiText, List<ProductUi>> = emptyMap(),
) {
    val productSections = products
        .toList()
        .map { (category, products) ->
            ProductSection(category, products)
        }
}