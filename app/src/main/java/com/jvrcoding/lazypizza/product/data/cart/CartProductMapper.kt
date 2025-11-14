package com.jvrcoding.lazypizza.product.data.cart

import com.jvrcoding.lazypizza.core.data.database.product.ProductEntity
import com.jvrcoding.lazypizza.core.data.database.product_topping_relation.ProductWithToppings
import com.jvrcoding.lazypizza.core.data.database.topping.ToppingEntity
import com.jvrcoding.lazypizza.product.domain.cart.CartProduct
import com.jvrcoding.lazypizza.product.domain.cart.ProductTopping
import java.time.Instant

fun ProductWithToppings.toCartProduct(): CartProduct {
    return CartProduct(
        uid = product.uid,
        productId = product.productId,
        name = product.name,
        totalPrice = product.price.toBigDecimal(),
        description = product.description,
        imageUrl = product.imageUrl,
        quantity = product.quantity,
        productToppings = toppings.map {
            ProductTopping(
                id = it.toppingId,
                name = it.name,
                quantity = it.quantity,
                price = it.price.toBigDecimal(),
                productId = it.productUid
            )
        },
        createdAt = Instant.ofEpochMilli(this.product.createdAt),
    )
}

fun CartProduct.toProductWithToppings(): ProductWithToppings {
    return ProductWithToppings(
        product = ProductEntity(
            productId = productId,
            name = name,
            price = totalPrice.toDouble(),
            description = description,
            imageUrl = imageUrl,
            quantity = quantity,
            createdAt = createdAt.toEpochMilli()

        ),
        toppings = productToppings.map {
            ToppingEntity(
                toppingId = it.id,
                name = it.name,
                quantity = it.quantity,
                price = it.price.toDouble(),
                productUid = 0
            )
        }
    )
}
