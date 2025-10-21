package com.jvrcoding.lazypizza.product.presentation.model

import com.jvrcoding.lazypizza.R

enum class Tab(val label: String, val icon: Int) {
    MENU("Menu", R.drawable.ic_book),
    CART("Cart", R.drawable.ic_cart),
    HISTORY("History", R.drawable.ic_clock)
}