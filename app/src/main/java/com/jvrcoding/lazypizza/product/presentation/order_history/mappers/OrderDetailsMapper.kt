package com.jvrcoding.lazypizza.product.presentation.order_history.mappers

import com.jvrcoding.lazypizza.core.presentation.util.toFormatted
import com.jvrcoding.lazypizza.product.domain.order.OrderDetails
import com.jvrcoding.lazypizza.product.presentation.order_history.models.OrderDetailsUi

fun OrderDetails.toOrderDetailsUi(): OrderDetailsUi {
    return OrderDetailsUi(
        orderId = id,
        orderNumber = "Order $orderNumber",
        items = products.joinToString("\n") { "${it.quantity}x ${it.name}" },
        orderCreated = createdAt.toFormatted("MMMM dd, HH:mm"),
        status = status,
        totalAmount = "$$totalAmount"
    )
}