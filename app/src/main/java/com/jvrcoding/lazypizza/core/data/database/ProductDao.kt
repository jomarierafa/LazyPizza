package com.jvrcoding.lazypizza.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jvrcoding.lazypizza.core.data.database.product_topping_relation.ProductWithToppings
import com.jvrcoding.lazypizza.core.data.database.product.ProductEntity
import com.jvrcoding.lazypizza.core.data.database.topping.ToppingEntity
import com.jvrcoding.lazypizza.product.domain.cart.ProductTopping
import kotlinx.coroutines.flow.Flow

// TODO(rename all product to cart)
@Dao
interface ProductDao {

    @Query("SELECT COUNT(*) FROM productentity")
    fun observeProductCount(): Flow<Int>

    @Transaction
    @Query("SELECT * FROM productentity ORDER BY createdAt DESC")
    fun observeCartProducts(): Flow<List<ProductWithToppings>>

    @Insert
    suspend fun insertProduct(product: ProductEntity): Long

    @Insert
    suspend fun insertToppings(toppings: List<ToppingEntity>)

    @Transaction
    suspend fun insertProductWithToppings(productWithToppings: ProductWithToppings) {
        val uid = insertProduct(productWithToppings.product)

        val toppings = productWithToppings.toppings.map { topping ->
            topping.copy(productUid = uid.toInt())
        }
        insertToppings(toppings)
    }

//    @Transaction
//    suspend fun insertProductWithToppings(product: ProductEntity, toppings: List<ProductTopping>) {
//        val productId = insertProduct(product)
//        val toppings = toppings.map { topping ->
//            ToppingEntity(
//                name = topping.name,
//                quantity = topping.quantity,
//                price = topping.price.toDouble(),
//                toppingId = topping.id,
//                productUid = productId.toInt()
//            )
//        }
//        insertToppings(toppings)
//    }

    @Query("DELETE FROM ProductEntity WHERE uid = :uid")
    suspend fun deleteProduct(uid: Int)

    @Query("DELETE FROM productentity")
    suspend fun deleteAllProducts()

    @Query("UPDATE productentity SET quantity = :quantity WHERE uid = :uid")
    suspend fun updateQuantity(uid: Int, quantity: Int)
}