package com.jvrcoding.lazypizza.product.data.order

import com.google.firebase.Timestamp
import com.jvrcoding.lazypizza.product.domain.cart.CartProduct
import com.jvrcoding.lazypizza.product.domain.cart.ProductTopping
import com.jvrcoding.lazypizza.product.domain.order.OrderDetails
import java.time.Instant

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

fun OrderDetailsDto.toOrderDetails(orderId: String): OrderDetails  {
    return OrderDetails(
        id = orderId,
        userId = userId,
        orderNumber = orderNumber,
        pickupTime = pickupTime.toDate().toInstant(),
        createdAt = createdAt.toDate().toInstant(),
        totalAmount = totalAmount.toBigDecimal(),
        status = status,
        comment = comment,
        products = products.map { it.toCartProduct() },

    )
}

fun CartProduct.toOrderProductDto(): OrderProductDto {
    return OrderProductDto(
        id = productId,
        name = name,
        price = totalPrice.toDouble(),
        quantity = quantity,
        productToppings = productToppings.map { it.toOrderProductToppingDto() }
    )
}

fun OrderProductDto.toCartProduct(): CartProduct {
    return CartProduct(
        productId = id,
        name = name,
        totalPrice = price.toBigDecimal(),
        description = "",
        imageUrl = "",
        quantity = quantity,
        createdAt = Instant.now(),
        productToppings = emptyList()
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