package com.jvrcoding.lazypizza.app.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.jvrcoding.lazypizza.app.LazyPizzaApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as LazyPizzaApp).applicationScope
    }
    single { Firebase.firestore }
}