package com.jvrcoding.lazypizza.product.presentation.model

import com.jvrcoding.lazypizza.R

enum class Tab(val label: String, val icon: Int) {
    Menu("Menu", R.drawable.ic_book),
    Cart("Cart", R.drawable.ic_cart),
    History("History", R.drawable.ic_clock)
}