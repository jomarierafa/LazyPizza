package com.jvrcoding.lazypizza.core.database.product_topping_relation

import androidx.room.Embedded
import androidx.room.Relation
import com.jvrcoding.lazypizza.core.database.product.ProductEntity
import com.jvrcoding.lazypizza.core.database.topping.ToppingEntity


data class ProductWithToppings(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "uid",
        entityColumn = "productUid",
    )
    val toppings: List<ToppingEntity>
)