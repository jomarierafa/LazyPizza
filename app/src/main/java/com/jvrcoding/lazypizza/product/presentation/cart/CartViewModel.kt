package com.jvrcoding.lazypizza.product.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.lazypizza.core.presentation.util.currencyToBigDecimal
import com.jvrcoding.lazypizza.product.domain.cart.CartDataSource
import com.jvrcoding.lazypizza.product.domain.cart.CartProduct
import com.jvrcoding.lazypizza.product.domain.product.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.toCartProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.toRecommendProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant

class CartViewModel(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CartState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeCartProducts()
                observeRecommendedProducts()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CartState()
        )

    fun onAction(action: CartAction) {
        when(action) {
            is CartAction.OnRemoveItem -> removeProductItem(action.productUid)
            is CartAction.OnAddProduct -> addProduct(action.productUi)
        }
    }

    private fun observeCartProducts() {
        cartDataSource.observeCartProducts()
            .onEach { products ->
                _state.update { it.copy(
                    products = products.map { product -> product.toCartProductUi() }
                ) }
            }.launchIn(viewModelScope)
    }

    private fun observeRecommendedProducts() {
        productDataSource.getRecommendedProducts()
            .onEach { products ->
                _state.update { it.copy(
                    recommendedProducts = products.map {
                        product -> product.toRecommendProductUi()
                    }.shuffled()
                ) }
            }.launchIn(viewModelScope)
    }

    fun removeProductItem(productUid: Int) {
        viewModelScope.launch {
            cartDataSource.deleteCartItem(productUid)
        }
    }

    fun addProduct(productUi: RecommendedProductUi) {
        viewModelScope.launch {
            val product = CartProduct(
                productId = productUi.id,
                name = productUi.name,
                totalPrice = productUi.price.currencyToBigDecimal(),
                description = "",
                imageUrl = productUi.imageUrl,
                quantity = 1,
                createdAt = Instant.now()
            )
            cartDataSource.insertCartProducts(product)
        }
    }
}