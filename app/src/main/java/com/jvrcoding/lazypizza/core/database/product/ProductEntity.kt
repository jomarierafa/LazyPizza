package com.jvrcoding.lazypizza.core.database.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val productId: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String,
    val createdAt: Long,
)
