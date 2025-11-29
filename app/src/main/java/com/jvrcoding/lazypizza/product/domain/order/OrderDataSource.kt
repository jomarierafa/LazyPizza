package com.jvrcoding.lazypizza.product.domain.order

import kotlinx.coroutines.flow.Flow


interface OrderDataSource {

    fun getOrders(): Flow<List<OrderDetails>>
    suspend fun insertOrder(orderDetails: OrderDetails): String?
}