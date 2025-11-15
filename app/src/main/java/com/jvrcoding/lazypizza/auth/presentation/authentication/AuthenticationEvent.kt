package com.jvrcoding.lazypizza.auth.presentation.authentication

sealed interface AuthenticationEvent {
    data object SuccessfulAuthentication: AuthenticationEvent
}