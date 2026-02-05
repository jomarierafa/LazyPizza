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
    private val firebaseAuth: FirebaseAuth,
    private val orderDataSource: OrderDataSource
): ViewModel() {

    private var hasLoadedInitialData = false

    private val authListener = FirebaseAuth.AuthStateListener { auth ->
        val isSignedIn = auth.currentUser != null

        _state.update {
            it.copy(isUserSignedIn = isSignedIn)
        }
    }


    private val _state = MutableStateFlow(OrderHistoryState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                getOrders()
                firebaseAuth.addAuthStateListener(authListener)
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = OrderHistoryState()
        )


    private fun getOrders() {
        _state.update { it.copy(fetchingOrders = true) }
        orderDataSource
            .getOrders()
            .onEach { orders ->
                _state.update { it.copy(
                    fetchingOrders = false,
                    orders = orders.map { it.toOrderDetailsUi() }
                ) }
            }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        firebaseAuth.removeAuthStateListener(authListener)
        super.onCleared()
    }

}