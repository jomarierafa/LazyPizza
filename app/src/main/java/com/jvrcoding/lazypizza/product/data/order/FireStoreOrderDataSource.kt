package com.jvrcoding.lazypizza.product.data.order

import com.google.firebase.firestore.FirebaseFirestore
import com.jvrcoding.lazypizza.product.domain.order.OrderDataSource
import com.jvrcoding.lazypizza.product.domain.order.OrderDetails
import kotlinx.coroutines.tasks.await

class FireStoreOrderDataSource(
    private val firestore: FirebaseFirestore
): OrderDataSource {

    override suspend fun insertOrder(orderDetails: OrderDetails): String? {
        try {
            val docRef = firestore.collection("orders").document()
            val dto = orderDetails.copy(id = docRef.id).toOrderDetailsDto()

            docRef.set(dto).await()

            return docRef.id
        } catch (e: Exception) {
            return null
        }
    }
}