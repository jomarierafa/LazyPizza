package com.jvrcoding.lazypizza.core.database.topping

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jvrcoding.lazypizza.core.database.product.ProductEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["uid"],
            childColumns = ["productUid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("productUid")]
)
data class ToppingEntity(
    @PrimaryKey(autoGenerate = true)
    val toppingUid: Int = 0,
    val toppingId: String,
    val productUid: Int,
    val name: String,
    val quantity: Int,
    val price: Double
)