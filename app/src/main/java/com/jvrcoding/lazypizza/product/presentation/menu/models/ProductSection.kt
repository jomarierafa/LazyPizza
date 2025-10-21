package com.jvrcoding.lazypizza.product.presentation.menu.models

import com.jvrcoding.lazypizza.core.presentation.util.UiText

data class ProductSection(
    val category: UiText,
    val products: List<ProductUi>
)
