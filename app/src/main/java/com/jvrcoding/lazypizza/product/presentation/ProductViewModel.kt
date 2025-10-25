package com.jvrcoding.lazypizza.product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.lazypizza.product.domain.cart.CartDataSource
import com.jvrcoding.lazypizza.product.presentation.model.Tab
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val cartDataSource: CartDataSource
): ViewModel() {

    private val eventChannel = Channel<ProductEvent>()
    val events = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProductState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeCartItemCount()
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
            is ProductAction.OnBottomNavigationItemClick -> onBottomNavigationItemClick(action.tab)
            else -> Unit
        }

    }

    private fun observeCartItemCount() {
        cartDataSource
            .observeCartItemCount()
            .onEach { count ->
                _state.update { it.copy(
                    cartItemCount = count
                ) }
            }.launchIn(viewModelScope)
    }

    private fun onBottomNavigationItemClick(selectedTab: Tab) {
        if(state.value.selectedTab == selectedTab) return
        viewModelScope.launch {
            _state.update { it.copy(
                selectedTab = selectedTab
            ) }
            eventChannel.send(
                when(selectedTab) {
                    Tab.MENU -> ProductEvent.NavigateToMenu
                    Tab.CART -> ProductEvent.NavigateToCart
                    Tab.HISTORY -> ProductEvent.NavigateToHistory
                }
            )
        }
    }
}