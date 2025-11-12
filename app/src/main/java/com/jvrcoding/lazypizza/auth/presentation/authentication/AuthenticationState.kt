package com.jvrcoding.lazypizza.auth.presentation.authentication

import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.util.UiText

data class AuthenticationState(
    val phoneNumber: String = "",
    val isPhoneNumberValid: Boolean = false,
    val isVerificationPhase: Boolean = false,
    val code: List<Int?> = (1..6).map { null },
    val focusedIndex: Int? = null,
    val showIncorrectCodeMessage: Boolean = false,
    val remainingTime: Int = 60,
    val showResendTimer: Boolean = true
) {
    val buttonText: UiText
        get() = if(isVerificationPhase)
            UiText.StringResource(R.string.confirm)
        else
            UiText.StringResource(R.string._continue)
}