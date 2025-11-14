package com.jvrcoding.lazypizza.app

import android.app.Application
import com.jvrcoding.lazypizza.app.di.appModule
import com.jvrcoding.lazypizza.auth.di.authModule
import com.jvrcoding.lazypizza.core.di.coreModule
import com.jvrcoding.lazypizza.product.di.productModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LazyPizzaApp: Application() {
    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LazyPizzaApp)
            modules(
                appModule,
                authModule,
                productModule,
                coreModule
            )
        }
    }
}