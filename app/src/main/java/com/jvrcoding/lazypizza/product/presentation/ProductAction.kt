package com.jvrcoding.lazypizza.product.presentation

import com.jvrcoding.lazypizza.product.domain.Product
import com.jvrcoding.lazypizza.product.presentation.model.Tab

sealed interface ProductAction {
    data class OnBottomNavigationItemClick(val tab: Tab): ProductAction
    data class OnProductClick(val product: Product): ProductAction
}

