package com.jvrcoding.lazypizza.product.presentation.menu

import com.jvrcoding.lazypizza.product.presentation.menu.models.ProductUi


sealed interface MenuAction {
    data class OnMenuClick(val product: ProductUi): MenuAction
    data class OnSearchQueryChange(val query: String): MenuAction
    data class OnAddToCardClick(val product: ProductUi): MenuAction
    data class OnMinusClick(val productId: String): MenuAction
    data class OnAddClick(val productId: String): MenuAction
    data class OnRemoveClick(val productId: String): MenuAction
}