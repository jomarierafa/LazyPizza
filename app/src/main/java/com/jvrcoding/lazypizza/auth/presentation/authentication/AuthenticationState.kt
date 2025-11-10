package com.jvrcoding.lazypizza.auth.presentation.authentication

data class AuthenticationState(
    val code: List<Int?> = (1..6).map { null },
    val focusedIndex: Int? = null,
    val isValid: Boolean? = null
)