package com.jvrcoding.lazypizza.product.presentation.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.lazypizza.core.presentation.util.currencyToBigDecimal
import com.jvrcoding.lazypizza.product.domain.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.product_details.models.SelectedTopping
import com.jvrcoding.lazypizza.product.presentation.product_details.util.toToppingUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

class ProductDetailsViewModel(
    private val productDataSource: ProductDataSource,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProductDetailsState(
        imageUrl = savedStateHandle["productImage"] ?: "",
        productName = savedStateHandle["productName"] ?: "",
        description = savedStateHandle["productDescription"] ?: "",
        basePrice = savedStateHandle.get<String>("productPrice")?.toBigDecimal() ?: BigDecimal.ZERO
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
            is ProductDetailsAction.OnAddQuantity -> onAddQuantity(action.toppingId)
            is ProductDetailsAction.OnReduceQuantity -> onReduceQuantity(action.toppingId)
            is ProductDetailsAction.OnToppingSelect -> onToppingSelect(action.toppingId)
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

    private fun onToppingSelect(toppingId: String) {
        val current = state.value.selectedToppings.toMutableList()

        if(current.any { it.id == toppingId }) {
            return
        }
        current.add(
            SelectedTopping(
                id = toppingId,
                quantity = 1,
                price = state.value.toppings.first {
                    it.id == toppingId
                }.price.currencyToBigDecimal()
            )
        )

        _state.update { it.copy(
            selectedToppings = current
        ) }
    }

    private fun onAddQuantity(toppingId: String) {
        val current = state.value.selectedToppings.toMutableList()
        val index = current.indexOfFirst { it.id == toppingId }
        if (index != -1) {
            val item = current[index]
            val newQuantity = item.quantity + 1
            current[index] = item.copy(quantity = newQuantity)
            _state.update { it.copy(
                selectedToppings = current
            ) }
        }
    }

    private fun onReduceQuantity(toppingId: String) {
        val current = state.value.selectedToppings.toMutableList()
        val index = current.indexOfFirst { it.id == toppingId }
        if (index != -1) {
            val item = current[index]
            val newQuantity = item.quantity - 1
            if (newQuantity <= 0) current.removeAt(index)
            else current[index] = item.copy(quantity = newQuantity)
            _state.update { it.copy(
                selectedToppings = current
            ) }
        }
    }
}