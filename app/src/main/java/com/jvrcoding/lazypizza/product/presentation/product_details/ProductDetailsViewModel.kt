package com.jvrcoding.lazypizza.product.presentation.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.lazypizza.product.domain.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.product_details.util.toToppingUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProductDetailsViewModel(
    private val productDataSource: ProductDataSource,
    private val saveStateHandle: SavedStateHandle
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProductDetailsState(
        imageUrl = saveStateHandle["productImage"] ?: "",
        productName = saveStateHandle["productName"] ?: "",
        description = saveStateHandle["productDescription"] ?: "",
    ))
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                getToppings()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ProductDetailsState()
        )

    fun onAction(action: ProductDetailsAction) {
        when (action) {
            is ProductDetailsAction.OnAddQuantity -> TODO()
            is ProductDetailsAction.OnReduceQuantity -> TODO()
            is ProductDetailsAction.OnToppingSelect -> TODO()
            else -> Unit
        }
    }

    private fun getToppings() {
        productDataSource
            .getProductToppings()
            .onEach { toppings ->
                _state.update { it.copy(
                    toppings = toppings.map { it.toToppingUi() }
                ) }
            }.launchIn(viewModelScope)
    }
}