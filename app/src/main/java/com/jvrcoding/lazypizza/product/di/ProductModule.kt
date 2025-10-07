package com.jvrcoding.lazypizza.product.di

import com.jvrcoding.lazypizza.product.data.FireStoreProductDataSource
import com.jvrcoding.lazypizza.product.domain.ProductDataSource
import com.jvrcoding.lazypizza.product.presentation.product_list.ProductViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val productModule = module {
    singleOf(::FireStoreProductDataSource) bind ProductDataSource::class

    viewModelOf(::ProductViewModel)

}