package com.jvrcoding.lazypizza.product.presentation.product_list


sealed interface ProductAction {
    data class OnSearchQueryChange(val query: String): ProductAction
    data class OnAddToCardClick(val productId: String): ProductAction
    data class OnMinusClick(val productId: String): ProductAction
    data class OnAddClick(val productId: String): ProductAction
    data class OnRemoveClick(val productId: String): ProductAction
}