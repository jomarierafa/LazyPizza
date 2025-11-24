package com.jvrcoding.lazypizza.product.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.lazypizza.product.domain.cart.CartDataSource
import com.jvrcoding.lazypizza.product.domain.product.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.cart.model.toCartProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.toRecommendProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CheckoutViewModel(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource
): ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CheckoutState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeOrderDetails()
                observeRecommendedProducts()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CheckoutState()
        )

    private fun observeOrderDetails() {
        cartDataSource.observeCartProducts()
            .onEach { products ->
                _state.update { it.copy(
                    products = products.map { product -> product.toCartProductUi() }
                ) }
            }.launchIn(viewModelScope)
    }

    private fun observeRecommendedProducts() {
        combine(
            cartDataSource.observeCartProducts(),
            productDataSource.getRecommendedProducts()
        ) { cartProducts, recommendedProducts ->
            _state.update {
                it.copy(
                    recommendedProducts = recommendedProducts
                        .filter { product -> cartProducts.none { cart -> cart.productId == product.id } }
                        .map { product -> product.toRecommendProductUi() }
                )
            }
        }.launchIn(viewModelScope)
    }

}