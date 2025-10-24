package com.jvrcoding.lazypizza.product.presentation.product_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.lazypizza.core.presentation.util.currencyToBigDecimal
import com.jvrcoding.lazypizza.product.domain.cart.CartDataSource
import com.jvrcoding.lazypizza.product.domain.cart.CartProduct
import com.jvrcoding.lazypizza.product.domain.cart.ProductTopping
import com.jvrcoding.lazypizza.product.domain.product.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.product_details.mappers.toToppingUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.Instant

class ProductDetailsViewModel(
    private val productDataSource: ProductDataSource,
    private val cartDataSource: CartDataSource,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProductDetailsState(
        productId = savedStateHandle["productId"] ?: "",
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
            ProductDetailsAction.OnAddToCartClick -> onAddToCartClick()
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

    private fun onAddToCartClick() {
        viewModelScope.launch {
            val product = CartProduct(
                productId = state.value.productId,
                name = state.value.productName,
                totalPrice = state.value.totalPrice,
                description = state.value.description,
                imageUrl = state.value.imageUrl,
                quantity = 1,
                createdAt = Instant.now(),
                productToppings = state.value.selectedToppings
            )
            cartDataSource.insertCartProducts(product)
        }
    }

    private fun onToppingSelect(toppingId: String) {
        val current = state.value.selectedToppings.toMutableList()

        if(current.any { it.id == toppingId }) {
            return
        }
        val topping = state.value.toppings.first {
            it.id == toppingId
        }
        current.add(
            ProductTopping(
                id = toppingId,
                name = topping.name,
                quantity = 1,
                price = topping.price.currencyToBigDecimal(),
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