package com.jvrcoding.lazypizza.core.domain.auth

interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}