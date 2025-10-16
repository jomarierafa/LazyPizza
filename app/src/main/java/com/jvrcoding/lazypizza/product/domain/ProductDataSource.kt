package com.jvrcoding.lazypizza.product.domain

import kotlinx.coroutines.flow.Flow

interface ProductDataSource {
    fun getProducts(): Flow<List<Product>>
    fun getProductToppings(): Flow<List<Topping>>
}