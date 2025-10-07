package com.jvrcoding.lazypizza.product.presentation.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.lazypizza.core.presentation.util.UiText
import com.jvrcoding.lazypizza.product.domain.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.product_list.models.ProductUi
import com.jvrcoding.lazypizza.product.presentation.product_list.util.toProductUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProductViewModel(
    val productDataSource: ProductDataSource
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



    private fun observeProducts() {
        productDataSource.getProducts()
            .map { products ->
                products.map { it.toProductUi() }
            }
            .groupByCategory()
            .onEach { groupedProduct ->
                _state.update { it.copy(products = groupedProduct) }
            }
            .launchIn(viewModelScope)
    }

    private fun Flow<List<ProductUi>>.groupByCategory(): Flow<Map<UiText, List<ProductUi>>> {
        return map { products ->
            products
                .groupBy { product -> UiText.Dynamic(product.type) }
        }
    }

}