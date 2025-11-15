package com.jvrcoding.lazypizza.product.presentation.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class OrderHistoryViewModel(
    firebaseAuth: FirebaseAuth
): ViewModel() {

    private var hasLoadedInitialData = false


    private val _state = MutableStateFlow(OrderHistoryState(
        isUserSignedIn = firebaseAuth.currentUser != null
    ))
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = OrderHistoryState()
        )

}