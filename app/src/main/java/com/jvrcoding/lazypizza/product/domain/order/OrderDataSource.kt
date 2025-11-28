package com.jvrcoding.lazypizza.product.domain.order


interface OrderDataSource {

    suspend fun insertOrder(orderDetails: OrderDetails): String?
}