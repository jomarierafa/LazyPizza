package com.jvrcoding.lazypizza.product.presentation.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.lazypizza.core.presentation.util.UiText
import com.jvrcoding.lazypizza.product.domain.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.product_list.models.ProductUi
import com.jvrcoding.lazypizza.product.presentation.product_list.models.SelectedProduct
import com.jvrcoding.lazypizza.product.presentation.product_list.util.toProductUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProductViewModel(
    private val productDataSource: ProductDataSource
): ViewModel() {

    private var hasLoadedInitialData = false

    private val searchQuery = MutableStateFlow("")

    private val _state = MutableStateFlow(ProductState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeProducts()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ProductState()
        )

    fun onAction(action: ProductAction) {
        when(action) {
            is ProductAction.OnSearchQueryChange -> onSearchQueryChange(action.query)
            is ProductAction.OnAddToCardClick -> onAddToCardClick(action.productId)
            is ProductAction.OnAddClick -> onAddClick(action.productId)
            is ProductAction.OnMinusClick -> onMinusClick(action.productId)
            is ProductAction.OnRemoveClick -> onRemoveClick(action.productId)
            else -> Unit
        }
    }

    private fun onAddToCardClick(productId: String) {
        val current = state.value.selectedProducts.toMutableList()

        current.add(
            SelectedProduct(
                productId = productId,
                quantity = 1,
                totalPrice = "0.00"
            )
        )

        _state.update { it.copy(
            selectedProducts = current
        ) }
    }

    private fun onAddClick(productId: String) {
        val current = state.value.selectedProducts.toMutableList()
        val index = current.indexOfFirst { it.productId == productId }
        if (index != -1) {
            val item = current[index]
            val newQuantity = item.quantity + 1
            current[index] = item.copy(quantity = newQuantity)
            _state.update { it.copy(
                selectedProducts = current
            ) }
        }
    }

    private fun onMinusClick(productId: String) {
        val current = state.value.selectedProducts.toMutableList()
        val index = current.indexOfFirst { it.productId == productId }
        if (index != -1) {
            val item = current[index]
            val newQuantity = item.quantity - 1
            if (newQuantity <= 0) current.removeAt(index)
            else current[index] = item.copy(quantity = newQuantity)
            _state.update { it.copy(
                selectedProducts = current
            ) }
        }
    }

    private fun onRemoveClick(productId: String) {
        _state.update { it.copy(
            selectedProducts = it.selectedProducts.filterNot { it.productId == productId }
        ) }
    }


    private fun observeProducts() {
        combine(
            productDataSource.getProducts(),
            searchQuery
                .debounce(300L)
                .distinctUntilChanged()
        ) { products, searchQuery ->
            val filtered = if (searchQuery.isBlank()) {
                products
            } else {
                products.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }
            }
            filtered.map { it.toProductUi() }

        }.groupByCategory()
            .onEach { groupedProduct ->
                _state.update { it.copy(products = groupedProduct) }
            }
            .launchIn(viewModelScope)
    }

    private fun onSearchQueryChange(query: String) {
        searchQuery.value = query.trim()
        _state.update { it.copy(searchQuery = query) }
    }


    private fun Flow<List<ProductUi>>.groupByCategory(): Flow<Map<UiText, List<ProductUi>>> {
        return map { products ->
            products
                .groupBy { product -> UiText.Dynamic(product.type) }
        }
    }

}