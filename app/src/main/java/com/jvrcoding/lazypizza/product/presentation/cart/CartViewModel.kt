package com.jvrcoding.lazypizza.product.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.lazypizza.product.domain.cart.CartDataSource
import com.jvrcoding.lazypizza.product.presentation.cart.model.toCartProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartDataSource: CartDataSource
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CartState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeCartProducts()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CartState()
        )

    private fun observeCartProducts() {
        cartDataSource.observeCartProducts()
            .onEach { products ->
                _state.update { it.copy(
                    products = products.map { product -> product.toCartProductUi() }
                ) }
            }.launchIn(viewModelScope)
    }

    fun onAction(action: CartAction) {
        when(action) {
            is CartAction.OnRemoveItem -> removeProductItem(action.productUid)
        }
    }

    fun removeProductItem(productUid: Int) {
        viewModelScope.launch {
            cartDataSource.deleteCartItem(productUid)
        }
    }
}