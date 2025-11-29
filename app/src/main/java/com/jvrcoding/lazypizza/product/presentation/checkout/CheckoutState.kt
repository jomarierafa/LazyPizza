package com.jvrcoding.lazypizza.product.presentation.checkout

import com.jvrcoding.lazypizza.core.presentation.util.UiText
import com.jvrcoding.lazypizza.core.presentation.util.currencyToBigDecimal
import com.jvrcoding.lazypizza.product.presentation.cart.model.CartProductUi
import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi
import com.jvrcoding.lazypizza.product.presentation.checkout.models.PickupTime
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class CheckoutState(
    val selectedOption: PickupTime = PickupTime.EARLIEST,
    val products: List<CartProductUi> = emptyList(),
    val recommendedProducts: List<RecommendedProductUi> = emptyList(),
    val comment: String = "",
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val timePickerErrorMessage: UiText? = null,
    val showTransactionSummary: Boolean = false,
    val orderNo: String = "",
    val isPlacingOrder: Boolean = false,

    val dateMillis: Long? = null,
    val hour: Int? = null,
    val minute: Int? = null,
) {
    val pickupTime: String
        get() {
            // --- RULE: If missing values, default to now + 15 minutes ---
            val defaultDateTime = LocalDateTime
                .now()
                .plusMinutes(15)
                .atZone(ZoneId.systemDefault())

            // If date is null → use default
            val finalDateMillis = dateMillis ?: defaultDateTime.toInstant().toEpochMilli()

            val selectedDate = Instant.ofEpochMilli(finalDateMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            // If time is null → use default time
            val finalHour = hour ?: defaultDateTime.hour
            val finalMinute = minute ?: defaultDateTime.minute

            // Combine into LocalDateTime
            val dateTime = selectedDate.atTime(finalHour, finalMinute)

            val today = LocalDate.now()

            return if (selectedDate == today) {
                dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            } else {
                dateTime.format(DateTimeFormatter.ofPattern("MMMM d, HH:mm"))
            }
        }

    val totalPrice: BigDecimal = products.sumOf {
        it.totalPrice.currencyToBigDecimal().times(it.quantity.toBigDecimal())
    }
}