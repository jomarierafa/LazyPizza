package com.jvrcoding.lazypizza.product.presentation.checkout.models

import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.util.UiText

enum class PickupTime(val value: UiText) {
    EARLIEST(UiText.StringResource(R.string.earliest_available_time)),
    SCHEDULE(UiText.StringResource(R.string.schedule_time))
}