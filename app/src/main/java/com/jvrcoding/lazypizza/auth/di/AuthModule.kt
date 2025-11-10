package com.jvrcoding.lazypizza.auth.di

import com.jvrcoding.lazypizza.auth.presentation.authentication.AuthenticationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::AuthenticationViewModel)
}