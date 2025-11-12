package com.jvrcoding.lazypizza.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.jvrcoding.lazypizza.auth.domain.UserDataValidator
import com.jvrcoding.lazypizza.auth.presentation.authentication.AuthenticationViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {

    single<FirebaseAuth> {
        val auth = FirebaseAuth.getInstance()
        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)
        auth
    }

    singleOf(::UserDataValidator)
    viewModelOf(::AuthenticationViewModel)
}