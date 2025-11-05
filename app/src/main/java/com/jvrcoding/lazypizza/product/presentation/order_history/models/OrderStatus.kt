package com.jvrcoding.lazypizza.product.presentation.order_history.models

import androidx.compose.ui.graphics.Color
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.Success
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.Warning
import com.jvrcoding.lazypizza.core.presentation.util.UiText

enum class OrderStatus(
    val title: UiText,
    val color: Color
) {
    IN_PROGRESS(
        title = UiText.StringResource(R.string.ongoing),
        color = Warning
    ),
    COMPLETED(
        title = UiText.StringResource(R.string.completed),
        color = Success
    )
}