package com.jvrcoding.lazypizza.core.di

import androidx.room.Room
import com.jvrcoding.lazypizza.core.data.auth.EncryptedSessionStorage
import com.jvrcoding.lazypizza.core.data.database.LazyPizzaDatabase
import com.jvrcoding.lazypizza.core.domain.auth.SessionStorage
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    single<LazyPizzaDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            LazyPizzaDatabase::class.java,
            "lazypizza.db"
        ).build()
    }
    single {
        get<LazyPizzaDatabase>().productDao
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}