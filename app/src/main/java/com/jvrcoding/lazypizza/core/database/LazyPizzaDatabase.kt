package com.jvrcoding.lazypizza.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jvrcoding.lazypizza.core.database.product.ProductEntity
import com.jvrcoding.lazypizza.core.database.topping.ToppingEntity

@Database(
    entities = [ProductEntity::class, ToppingEntity::class],
    version = 1
)
abstract class LazyPizzaDatabase: RoomDatabase() {
    abstract val productDao: ProductDao
}