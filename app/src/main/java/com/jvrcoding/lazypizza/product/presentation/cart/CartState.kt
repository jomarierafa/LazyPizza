package com.jvrcoding.lazypizza.product.presentation.cart

import com.jvrcoding.lazypizza.core.presentation.util.currencyToBigDecimal
import com.jvrcoding.lazypizza.product.presentation.cart.model.CartProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi
import java.math.BigDecimal

data class CartState(
    val products: List<CartProductUi> = emptyList(),
    val recommendedProducts: List<RecommendedProductUi> = emptyList()
) {
    val totalPrice: BigDecimal = products.sumOf {
        it.totalPrice.currencyToBigDecimal().times(it.quantity.toBigDecimal())
    }
}
