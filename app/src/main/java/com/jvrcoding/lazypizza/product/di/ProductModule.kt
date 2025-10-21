package com.jvrcoding.lazypizza.product.di

import com.jvrcoding.lazypizza.product.data.FireStoreProductDataSource
import com.jvrcoding.lazypizza.product.domain.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.ProductViewModel
import com.jvrcoding.lazypizza.product.presentation.product_details.ProductDetailsViewModel
import com.jvrcoding.lazypizza.product.presentation.menu.MenuViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val productModule = module {
    singleOf(::FireStoreProductDataSource) bind ProductDataSource::class

    viewModelOf(::ProductViewModel)
    viewModelOf(::MenuViewModel)
    viewModelOf(::ProductDetailsViewModel)

}