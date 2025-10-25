package com.jvrcoding.lazypizza.product.data.cart

import com.jvrcoding.lazypizza.core.database.ProductDao
import com.jvrcoding.lazypizza.product.domain.cart.CartDataSource
import com.jvrcoding.lazypizza.product.domain.cart.CartProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomCartDataSource(
    private val cartDao: ProductDao
): CartDataSource {

    override fun observeCartItemCount(): Flow<Int> {
        return cartDao.observeProductCount()
    }

    override fun observeCartProducts(): Flow<List<CartProduct>> {
        return cartDao
            .observeCartProducts()
            .map { productWithToppings ->
                productWithToppings.map { productWithTopping ->
                    productWithTopping.toCartProduct()
                }
            }
    }

    override suspend fun insertCartProducts(cartProducts: CartProduct) {
        cartDao.insertProductWithToppings(cartProducts.toProductWithToppings())
    }

    override suspend fun deleteCartItem(productUid: Int) {
        cartDao.deleteProduct(productUid)
    }

}