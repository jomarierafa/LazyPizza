package com.jvrcoding.lazypizza.core.data.auth

import com.jvrcoding.lazypizza.core.domain.auth.AuthInfo

fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        uid = uid,
        username = username
    )
}

fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(
        uid = uid,
        username = username
    )
}