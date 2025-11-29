package com.jvrcoding.lazypizza.product.presentation.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jvrcoding.lazypizza.product.domain.order.OrderDataSource
import com.jvrcoding.lazypizza.product.presentation.order_history.mappers.toOrderDetailsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class OrderHistoryViewModel(
    firebaseAuth: FirebaseAuth,
    private val orderDataSource: OrderDataSource
): ViewModel() {

    private var hasLoadedInitialData = false


    private val _state = MutableStateFlow(OrderHistoryState(
        isUserSignedIn = firebaseAuth.currentUser != null
    ))
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                getOrders()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = OrderHistoryState()
        )


    private fun getOrders() {
        orderDataSource
            .getOrders()
            .onEach { orders ->
                _state.update { it.copy(
                    orders = orders.map { it.toOrderDetailsUi() }
                ) }
            }.launchIn(viewModelScope)
    }

}