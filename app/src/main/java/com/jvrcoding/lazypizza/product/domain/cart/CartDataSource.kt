package com.jvrcoding.lazypizza.product.domain.cart

import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun observeCartItemCount(): Flow<Int>
    fun observeCartProducts(): Flow<List<CartProduct>>
    suspend fun insertCartProducts(cartProducts: CartProduct)
    suspend fun updateQuantity(productUid: Int, quantity: Int)
    suspend fun deleteCartItem(productUid: Int)
    suspend fun deleteAllCartItem()

}