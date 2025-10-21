package com.jvrcoding.lazypizza.product.presentation

import com.jvrcoding.lazypizza.product.presentation.model.Tab

data class ProductState(
    val selectedTab: Tab = Tab.MENU,
)
