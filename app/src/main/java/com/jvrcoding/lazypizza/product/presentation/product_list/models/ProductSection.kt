package com.jvrcoding.lazypizza.product.presentation.product_list.models

import com.jvrcoding.lazypizza.core.presentation.util.UiText

data class ProductSection(
    val category: UiText,
    val products: List<ProductUi>
)
