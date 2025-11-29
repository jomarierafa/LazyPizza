package com.jvrcoding.lazypizza.product.data.order

import com.google.firebase.firestore.FirebaseFirestore
import com.jvrcoding.lazypizza.product.domain.order.OrderDataSource
import com.jvrcoding.lazypizza.product.domain.order.OrderDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FireStoreOrderDataSource(
    private val firestore: FirebaseFirestore
): OrderDataSource {

    override fun getOrders(): Flow<List<OrderDetails>> = flow {
        try {
            val snapshot = firestore.collection("orders").get().await()
            val orders = snapshot.documents.mapNotNull {
                it.toObject(OrderDetailsDto::class.java)?.toOrderDetails(it.id)
            }

            emit(orders)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun insertOrder(orderDetails: OrderDetails): String? {
        try {
            val docRef = firestore.collection("orders").document()
            docRef.set(orderDetails.toOrderDetailsDto()).await()

            return docRef.id
        } catch (e: Exception) {
            return null
        }
    }
}