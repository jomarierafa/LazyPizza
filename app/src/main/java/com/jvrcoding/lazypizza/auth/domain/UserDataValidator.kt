package com.jvrcoding.lazypizza.auth.domain

class UserDataValidator {

    fun isValidPhoneNumber(number: String): Boolean {
        val regex = Regex("^\\+[1-9]\\d{5,14}$")
        return number.matches(regex)
    }
}