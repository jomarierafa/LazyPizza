package com.jvrcoding.lazypizza.product.domain.cart

import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun observeCartProducts(): Flow<List<CartProduct>>
    suspend fun insertCartProducts(cartProducts: CartProduct)
    suspend fun deleteCartItem(productUid: Int)
}