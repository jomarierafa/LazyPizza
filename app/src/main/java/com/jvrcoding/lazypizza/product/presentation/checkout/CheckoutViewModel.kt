package com.jvrcoding.lazypizza.product.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jvrcoding.lazypizza.auth.presentation.authentication.AuthenticationEvent
import com.jvrcoding.lazypizza.core.presentation.util.UiText
import com.jvrcoding.lazypizza.core.presentation.util.currencyToBigDecimal
import com.jvrcoding.lazypizza.product.domain.cart.CartDataSource
import com.jvrcoding.lazypizza.product.domain.cart.CartProduct
import com.jvrcoding.lazypizza.product.domain.order.OrderDataSource
import com.jvrcoding.lazypizza.product.domain.order.OrderDetails
import com.jvrcoding.lazypizza.product.domain.product.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.toCartProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.toRecommendProductUi
import com.jvrcoding.lazypizza.product.presentation.checkout.models.PickupTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class CheckoutViewModel(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource,
    private val orderDataSource: OrderDataSource,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private var tempDateHolder: Long = 0L
    private var orderProduct = emptyList<CartProduct>()

    private val eventChannel = Channel<AuthenticationEvent>()
    val events = eventChannel.receiveAsFlow()
    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(CheckoutState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeOrderDetails()
                observeRecommendedProducts()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CheckoutState()
        )

    fun onAction(action: CheckoutAction) {
        when(action) {
            is CheckoutAction.OnPickupTimeSelected -> onPickupTimeSelected(action.option)
            is CheckoutAction.OnDateSelected -> onDateSelected(action.date)
            is CheckoutAction.TimeSelected -> onTimeSelected(action.hour, action.minute)
            is CheckoutAction.OnRemoveItem -> removeProductItem(action.productUid)
            is CheckoutAction.OnAddProduct -> addProduct(action.productUi)
            is CheckoutAction.OnDecreaseQuantity -> decreaseQuantity(action.productUid)
            is CheckoutAction.OnIncreaseQuantity -> increaseQuantity(action.productUid)
            is CheckoutAction.OnCommentChange -> onCommentChange(action.comment)
            CheckoutAction.OnDismissDatePicker -> onDismissDatePicker()
            CheckoutAction.OnDismissTimePicker -> onDismissTimePicker()
            CheckoutAction.OnPlaceOrderClick -> placeOrder()
            else -> Unit
        }
    }

    private fun onCommentChange(comment: String) {
        _state.update { it.copy(comment = comment) }
    }


    private fun onDismissDatePicker() {
        _state.update { it.copy(showDatePicker = false) }
    }

    private fun onDismissTimePicker() {
        _state.update { it.copy(showTimePicker = false) }
    }

    private fun onPickupTimeSelected(option: PickupTime) {
        if(option == PickupTime.SCHEDULE) {
            _state.update { it.copy(showDatePicker = true) }
        } else {
            _state.update { it.copy(
                hour = null,
                minute = null,
                dateMillis = null,
                selectedOption = PickupTime.EARLIEST
            ) }
        }
    }

    private fun onDateSelected(date: Long) {
        tempDateHolder = date
        _state.update { it.copy(
            showDatePicker = false,
            showTimePicker = true
        ) }
    }

    private fun onTimeSelected(hour: Int, minute: Int) {
        val valid = isValidTime(hour, minute)
        if(!valid) {
            _state.update { it.copy(
                timePickerErrorMessage = UiText.Dynamic("Pickup available between 10:15 and 21:45")
            ) }
            return
        }

        val todayTimeValid  = isEarlierThanNowPlus15(tempDateHolder, hour, minute)
        if(todayTimeValid) {
            _state.update { it.copy(
                timePickerErrorMessage = UiText.Dynamic("Pickup is possible at least 15 minutes from the current time")
            ) }
            return
        }

        _state.update { it.copy(
            hour = hour,
            minute = minute,
            dateMillis = tempDateHolder,
            timePickerErrorMessage = null,
            showTimePicker = false,
            selectedOption = PickupTime.SCHEDULE
        ) }

    }

    private fun observeOrderDetails() {
        cartDataSource.observeCartProducts()
            .onEach { products ->
                orderProduct = products
                _state.update { it.copy(
                    products = products.map { product -> product.toCartProductUi() }
                ) }
            }.launchIn(viewModelScope)
    }

    private fun observeRecommendedProducts() {
        combine(
            cartDataSource.observeCartProducts(),
            productDataSource.getRecommendedProducts()
        ) { cartProducts, recommendedProducts ->
            _state.update {
                it.copy(
                    recommendedProducts = recommendedProducts
                        .filter { product -> cartProducts.none { cart -> cart.productId == product.id } }
                        .map { product -> product.toRecommendProductUi() }
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun increaseQuantity(productUid: Int) {
        viewModelScope.launch {
            cartDataSource.updateQuantity(
                productUid = productUid,
                quantity = state.value.products.first { it.id == productUid }.quantity + 1
            )
        }
    }

    private fun decreaseQuantity(productUid: Int) {
        viewModelScope.launch {
            cartDataSource.updateQuantity(
                productUid = productUid,
                quantity = state.value.products.first { it.id == productUid }.quantity - 1
            )
        }
    }

    private fun removeProductItem(productUid: Int) {
        viewModelScope.launch {
            cartDataSource.deleteCartItem(productUid)
        }
    }

    private fun addProduct(productUi: RecommendedProductUi) {
        viewModelScope.launch {
            val product = CartProduct(
                productId = productUi.id,
                name = productUi.name,
                totalPrice = productUi.price.currencyToBigDecimal(),
                description = "",
                imageUrl = productUi.imageUrl,
                quantity = 1,
                createdAt = Instant.now()
            )
            cartDataSource.insertCartProducts(product)
        }
    }

    private fun placeOrder() {
        _state.update { it.copy(isPlacingOrder = true) }
        viewModelScope.launch {
            val orderNo = "#${(10000..99999).random()}"
            val orderDetails = OrderDetails(
                userId = firebaseAuth.currentUser?.uid.toString(),
                orderNumber = orderNo,
                pickupTime = getPickupTime(),
                createdAt = Instant.now(),
                totalAmount = state.value.totalPrice,
                status = "In Progress",
                comment = state.value.comment,
                products = orderProduct
            )
            val id = orderDataSource.insertOrder(orderDetails)

            if(id != null) {
                cartDataSource.deleteAllCartItem()
                _state.update { it.copy(
                    showTransactionSummary = true,
                    orderNo = orderNo
                ) }
            } else {
                _state.update { it.copy(isPlacingOrder = false) }
            }
        }
    }


    private fun isValidTime(hour: Int, minute: Int): Boolean {
        val selected = hour * 60 + minute
        val start = 10 * 60 + 15    // 10:15
        val end = 21 * 60 + 45      // 21:45
        return selected in start..end
    }

    private fun isEarlierThanNowPlus15(dateMillis: Long, hour: Int, minute: Int): Boolean {
        val nowPlus15 = LocalDateTime.now().plusMinutes(15)

        val selectedDate = Instant.ofEpochMilli(dateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val selectedDateTime = selectedDate.atTime(hour, minute)

        if (selectedDate != LocalDate.now()) return false

        return selectedDateTime.isBefore(nowPlus15)
    }

    private fun getPickupTime(): Instant {
        val selectedDateTime = state.value.let {
            if(it.selectedOption == PickupTime.EARLIEST) {
                return Instant.now().plus(15, ChronoUnit.MINUTES)
            } else {
                val selectedDate = Instant.ofEpochMilli(it.dateMillis!!)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()

                selectedDate.atTime(it.hour!!, it.minute!!)
            }
        }

        return selectedDateTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
    }

}