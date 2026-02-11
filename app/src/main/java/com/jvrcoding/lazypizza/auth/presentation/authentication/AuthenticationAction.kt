package com.jvrcoding.lazypizza.auth.presentation.authentication

sealed interface AuthenticationAction {
    data class OnPhoneNumberChange(val phoneNumber: String): AuthenticationAction
    data class OnEnterNumber(val number: Int?, val index: Int): AuthenticationAction
    data class OnChangeFieldFocused(val index: Int): AuthenticationAction
    data object OnKeyboardBack: AuthenticationAction
    data object OnPrimaryButtonClick: AuthenticationAction
    data object ResendCodeButtonClick: AuthenticationAction
}