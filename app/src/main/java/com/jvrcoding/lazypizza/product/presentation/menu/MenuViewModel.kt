package com.jvrcoding.lazypizza.product.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jvrcoding.lazypizza.core.presentation.util.UiText
import com.jvrcoding.lazypizza.core.presentation.util.currencyToBigDecimal
import com.jvrcoding.lazypizza.product.domain.cart.CartDataSource
import com.jvrcoding.lazypizza.product.domain.cart.CartProduct
import com.jvrcoding.lazypizza.product.domain.product.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.menu.models.ProductUi
import com.jvrcoding.lazypizza.product.presentation.menu.models.SelectedProduct
import com.jvrcoding.lazypizza.product.presentation.menu.util.toProductUi
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
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.collections.first

class MenuViewModel(
    private val productDataSource: ProductDataSource,
    private val cartDataSource: CartDataSource,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private var hasLoadedInitialData = false

    private val searchQuery = MutableStateFlow("")

    private val _state = MutableStateFlow(MenuState(
        isUserSignedIn = firebaseAuth.currentUser != null
    ))
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeProducts()
                observeSelectedProducts()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MenuState()
        )

    fun onAction(action: MenuAction) {
        when(action) {
            is MenuAction.OnSearchQueryChange -> onSearchQueryChange(action.query)
            is MenuAction.OnAddToCardClick -> onAddToCardClick(action.product)
            is MenuAction.OnAddClick -> onAddClick(action.productId)
            is MenuAction.OnMinusClick -> onMinusClick(action.productId)
            is MenuAction.OnRemoveClick -> onRemoveClick(action.productId)
            MenuAction.OnLogoutClick -> onLogoutClick()
            MenuAction.OnConfirmLogoutClick -> onConfirmLogoutClick()
            MenuAction.OnDismissDialog -> onDismissDialog()
            else -> Unit
        }
    }

    private fun onLogoutClick() {
        _state.update { it.copy(
            showConfirmationDialog = true
        ) }
    }

    private fun onConfirmLogoutClick() {
        viewModelScope.launch {
            cartDataSource.deleteAllCartItem()
            firebaseAuth.signOut()
            _state.update { it.copy(
                isUserSignedIn = false,
                showConfirmationDialog = false
            ) }
        }
    }

    private fun onDismissDialog() {
        _state.update { it.copy(
            showConfirmationDialog = false
        ) }
    }

    private fun onAddToCardClick(product: ProductUi) {
        viewModelScope.launch {
            val product = CartProduct(
                productId = product.id,
                name = product.name,
                totalPrice = product.price.currencyToBigDecimal(),
                description = "",
                imageUrl = product.imageUrl,
                quantity = 1,
                createdAt = Instant.now()
            )
            cartDataSource.insertCartProducts(product)
        }
    }

    private fun onAddClick(productId: String) {
        viewModelScope.launch {
            val current = state.value.selectedProducts.toMutableList()
            val product = current.first { it.productId == productId }

            cartDataSource.updateQuantity(
                productUid = product.uid,
                quantity = product.quantity + 1
            )
        }
    }

    private fun onMinusClick(productId: String) {
        viewModelScope.launch {
            val current = state.value.selectedProducts.toMutableList()
            val product = current.first { it.productId == productId }

            if(product.quantity > 1) {
                cartDataSource.updateQuantity(
                    productUid = product.uid,
                    quantity = product.quantity - 1
                )
            } else {
                cartDataSource.deleteCartItem(product.uid)
            }
        }
    }

    private fun onRemoveClick(productId: String) {
        viewModelScope.launch {
            val current = state.value.selectedProducts.toMutableList()
            val product = current.first { it.productId == productId }
            cartDataSource.deleteCartItem(product.uid)
        }
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

    private fun observeSelectedProducts() {
        cartDataSource
            .observeCartProducts()
            .onEach { cartProducts ->
                _state.update { it.copy(
                    selectedProducts = cartProducts.map {
                        SelectedProduct(
                            uid = it.uid ?: 0,
                            productId = it.productId,
                            quantity = it.quantity
                        )
                    }
                ) }
            }.launchIn(viewModelScope)
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