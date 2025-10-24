package com.jvrcoding.lazypizza.core.database.di

import androidx.room.Room
import com.jvrcoding.lazypizza.core.database.LazyPizzaDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
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
}