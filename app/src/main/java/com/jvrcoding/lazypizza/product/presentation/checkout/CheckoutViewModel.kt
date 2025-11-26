package com.jvrcoding.lazypizza.product.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.lazypizza.product.domain.cart.CartDataSource
import com.jvrcoding.lazypizza.product.domain.product.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.cart.model.toCartProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.toRecommendProductUi
import com.jvrcoding.lazypizza.product.presentation.checkout.models.PickupTime
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

    fun onAction(action: CheckoutAction) {
        when(action) {
            is CheckoutAction.OnPickupTimeSelected -> onPickupTimeSelected(action.option)
            is CheckoutAction.OnDateSelected -> onDateSelected(action.date)
            CheckoutAction.OnDismissDatePicker -> onDismissDatePicker()
            CheckoutAction.OnDismissTimePicker -> onDismissTimePicker()
            else -> Unit
        }
    }


    private fun onDismissDatePicker() {
        _state.update { it.copy(showDatePicker = false) }
    }

    private fun onDismissTimePicker() {
        _state.update { it.copy(showTimePicker = false) }
    }

    private fun onPickupTimeSelected(option: PickupTime) {
        if(option == PickupTime.SCHEDULE) {
            _state.update { it.copy(showDatePicker = true) }
        }
//        _state.update { it.copy(selectedOption = option) }
    }

    private fun onDateSelected(date: Long) {
        _state.update { it.copy(
            showDatePicker = false,
            showTimePicker = true
        ) }
    }

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