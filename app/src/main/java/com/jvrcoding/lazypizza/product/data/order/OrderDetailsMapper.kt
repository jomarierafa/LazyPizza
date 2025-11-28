package com.jvrcoding.lazypizza.product.data.order

import com.google.firebase.Timestamp
import com.jvrcoding.lazypizza.product.domain.cart.CartProduct
import com.jvrcoding.lazypizza.product.domain.cart.ProductTopping
import com.jvrcoding.lazypizza.product.domain.order.OrderDetails

fun OrderDetails.toOrderDetailsDto() = OrderDetailsDto(
    userId = userId,
    orderNumber = orderNumber,
    pickupTime = Timestamp(pickupTime.epochSecond, pickupTime.nano),
    createdAt = Timestamp(createdAt.epochSecond, createdAt.nano),
    totalAmount = totalAmount.toDouble(),
    comment = comment,
    status = status,
    products = products.map { it.toOrderProductDto() }
)

fun CartProduct.toOrderProductDto(): OrderProductDto {
    return OrderProductDto(
        id = productId,
        name = name,
        price = totalPrice.toDouble(),
        quantity = quantity,
        productToppings = productToppings.map { it.toOrderProductToppingDto() }
    )
}

fun ProductTopping.toOrderProductToppingDto(): OrderProductToppingDto {
    return OrderProductToppingDto(
        id = id,
        name = name,
        quantity = quantity,
        price = price.toDouble()
    )
}