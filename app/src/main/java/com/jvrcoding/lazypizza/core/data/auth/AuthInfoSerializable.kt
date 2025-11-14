package com.jvrcoding.lazypizza.core.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoSerializable(
    val uid: String,
    val username: String
)
