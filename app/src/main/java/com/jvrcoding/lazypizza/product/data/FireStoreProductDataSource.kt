package com.jvrcoding.lazypizza.product.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jvrcoding.lazypizza.product.data.model.ProductDto
import com.jvrcoding.lazypizza.product.domain.Product
import com.jvrcoding.lazypizza.product.domain.ProductDataSource
import com.jvrcoding.lazypizza.product.domain.Topping
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.math.BigDecimal

class FireStoreProductDataSource(
    private val firestore: FirebaseFirestore
): ProductDataSource {

    // TODO(enhance implementation)
    override fun getProducts(): Flow<List<Product>> = flow {
//        firestore.collection("products")
//            .get()
//            .addOnSuccessListener { result ->
//                if(!result.isEmpty) {
//                    val productList = result.documents.mapNotNull { document ->
//                        document.toObject(ProductDto::class.java)?.copy(id = document.id)
//                    }
//                    Log.d("Firestore", "Document data: $productList")
//                } else {
//                    Log.d("Firestore", "No document")
//                }
//
//            }
//            .addOnFailureListener { e ->
//                Log.e("Firestore", "Error fetching document: ${e.message}")
//            }


        try {
            val snapshot = firestore.collection("products").get().await()
            val products = snapshot.documents.mapNotNull {
                it.toObject(ProductDto::class.java)?.copy(id = it.id)
            }

            emit(products.map { it.toProduct() })
        } catch (e: Exception) {
            throw e
        }

    }

    override fun getProductToppings(): Flow<List<Topping>> = flow {
        val toppingList = listOf(
            Topping("topping_1", "Bacon", BigDecimal("1.00"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416006/bacon_szeb8t.png"),
            Topping("topping_2", "Extra Cheese", BigDecimal("1.00"),  "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416007/cheese_frkn8b.png"),
            Topping("topping_3", "Corn", BigDecimal("0.50"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416009/corn_is7qax.png"),
            Topping("topping_4", "Tomato", BigDecimal("0.50"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416011/tomato_imipjb.png"),
            Topping("topping_5", "Olives", BigDecimal("0.50"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416008/olive_bp8gug.png"),
            Topping("topping_6", "Pepperoni", BigDecimal("1.00"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416010/pepperoni_gge70w.png"),
            Topping("topping_7", "Mushrooms", BigDecimal("0.50"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416009/mashroom_fy3tqy.png"),
            Topping("topping_8", "Basil", BigDecimal("0.50"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416008/basil_rlkpz1.png"),
            Topping("topping_9", "Pineapple", BigDecimal("1.00"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416010/pineapple_llzyrr.png"),
            Topping("topping_10", "Onion", BigDecimal("0.50"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416010/onion_ywuj0g.png"),
            Topping("topping_11", "Chili Peppers", BigDecimal("0.50"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416007/chilli_quojbb.png"),
            Topping("topping_12", "Spinach", BigDecimal("0.50"), "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416011/spinach_hsus2b.png"),
        )

        emit(toppingList)
    }


//    private suspend fun addData() {
//        val productList = listOf(
//            // Pizzas
//            Product(
//                id = "pizza_1",
//                name = "Margherita",
//                price = BigDecimal("8.99"),
//                description = "Ingredients: Tomato sauce, mozzarella, fresh basil, olive oil",
//                imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415998/Margherita_rwxxli.png",
//                type = "Pizza"
//            ),
//            Product(
//                id = "pizza_2",
//                name = "Pepperoni",
//                price = BigDecimal("9.99"),
//                description = "Ingredients: Tomato sauce, mozzarella, pepperoni",
//                imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416000/Pepperoni_izlaf4.png",
//                type = "Pizza"
//            ),
//            Product(
//                id = "pizza_3",
//                name = "Hawaiian",
//                price = BigDecimal("10.49"),
//                description = "Ingredients: Tomato sauce, mozzarella, ham, pineapple",
//                imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415998/Hawaiian_av87lh.png",
//                type = "Pizza"
//            ),
//            Product(
//                id = "pizza_4",
//                name = "BBQ Chicken",
//                price = BigDecimal("11.49"),
//                description = "Ingredients: BBQ sauce, mozzarella, grilled chicken, onion, corn",
//                imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415997/BBQ_Chicken_k6rgvh.png",
//                type = "Pizza"
//            ),
//            Product(
//                id = "pizza_5",
//                name = "Four Cheese",
//                price = BigDecimal("11.99"),
//                description = "Ingredients: Mozzarella, gorgonzola, parmesan, ricotta",
//                imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415998/Four_Cheese_v8h6bf.png",
//                type = "Pizza"
//            ),
//            Product(
//                id = "pizza_6",
//                name = "Veggie Delight",
//                price = BigDecimal("9.79"),
//                description = "Ingredients: Tomato sauce, mozzarella, mushrooms, olives, bell pepper, onion, corn",
//                imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416000/Veggie_Delight_lf00yw.png",
//                type = "Pizza"
//            ),
//            Product(
//                id = "pizza_7",
//                name = "Meat Lovers",
//                price = BigDecimal("12.49"),
//                description = "Ingredients: Tomato sauce, mozzarella, pepperoni, ham, bacon, sausage",
//                imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415998/Meat_Lovers_d6q1dm.png",
//                type = "Pizza"
//            ),
//            Product(
//                id = "pizza_8",
//                name = "Spicy Inferno",
//                price = BigDecimal("11.29"),
//                description = "Ingredients: Tomato sauce, mozzarella, spicy salami, jalapeños, red chili pepper, garlic",
//                imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416001/Spicy_Inferno_kzajja.png",
//                type = "Pizza"
//            ),
//            Product(
//                id = "pizza_9",
//                name = "Seafood Special",
//                price = BigDecimal("13.99"),
//                description = "Ingredients: Tomato sauce, mozzarella, shrimp, mussels, squid, parsley",
//                imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416000/Seafood_Special_x87f32.png",
//                type = "Pizza"
//            ),
//            Product(
//                id = "pizza_10",
//                name = "Truffle Mushroom",
//                price = BigDecimal("12.99"),
//                description = "Ingredients: Cream sauce, mozzarella, mushrooms, truffle oil, parmesan",
//                imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416001/Truffle_Mushroom_wes2el.png",
//                type = "Pizza"
//            ),
//
//            // Drinks
//            Product("drink_1", "Mineral Water", BigDecimal("1.49"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415984/mineral_water_b6xr9s.png", "Drinks"),
//            Product("drink_2", "7-Up", BigDecimal("1.89"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415983/7-up_idlbyz.png", "Drinks"),
//            Product("drink_3", "Pepsi", BigDecimal("1.99"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415983/pepsi_rzxmag.png", "Drinks"),
//            Product("drink_4", "Orange Juice", BigDecimal("2.49"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415983/orange_juice_bigvfe.png", "Drinks"),
//            Product("drink_5", "Apple Juice", BigDecimal("2.29"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415983/apple_juice_hvzwjp.png", "Drinks"),
//            Product("drink_6", "Iced Tea (Lemon)", BigDecimal("2.19"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415983/iced_tea_gj8z5m.png", "Drinks"),
//
//            // Sauces
//            Product("sauce_1", "Garlic Sauce", BigDecimal("0.59"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416003/Garlic_Sauce_orqjom.png", "Sauces"),
//            Product("sauce_2", "BBQ Sauce", BigDecimal("0.59"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416003/BBQ_Sauce_ntbtsy.png", "Sauces"),
//            Product("sauce_3", "Cheese Sauce", BigDecimal("0.89"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416003/Cheese_Sauce_z1ckqu.png", "Sauces"),
//            Product("sauce_4", "Spicy Chili Sauce", BigDecimal("0.59"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416005/Spicy_Chili_Sauce_nnf8zo.png", "Sauces"),
//
//            // Ice Cream
//            Product("icecream_1", "Vanilla Ice Cream", BigDecimal("2.49"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415990/vanilla_hfovwr.png", "Ice Cream"),
//            Product("icecream_2", "Chocolate Ice Cream", BigDecimal("2.49"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415990/chocolate_j65o9e.png", "Ice Cream"),
//            Product("icecream_3", "Strawberry Ice Cream", BigDecimal("2.49"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415991/strawberry_c9bjdc.png", "Ice Cream"),
//            Product("icecream_4", "Cookies Ice Cream", BigDecimal("2.79"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415990/cookies_wrrtcj.png", "Ice Cream"),
//            Product("icecream_5", "Pistachio Ice Cream", BigDecimal("2.99"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415990/pistachio_btm6fa.png", "Ice Cream"),
//            Product("icecream_6", "Mango Sorbet", BigDecimal("2.69"), "", "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759415990/mango_sorbet_ym5t6u.png", "Ice Cream")
//        )
//
//
//
//        val batch = firestore.batch()
//        val collection = firestore.collection("products")
//
//        productList.forEach { product ->
//            batch.set(
//                collection.document() ,
//                hashMapOf(
//                    "description" to product.description,
//                    "imageUrl" to product.imageUrl,
//                    "name" to product.name,
//                    "price" to product.price.toDouble(),
//                    "type" to product.type
//                )
//            )
//        }
//
//        try {
//            batch.commit().await() // ✅ Suspends until success or failure
//        } catch (e: Exception) {
//            throw e // 🔥 You can wrap this into domain-specific errors if needed
//        }
//    }
}