package com.jvrcoding.lazypizza.core.presentation.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.toFormatted(pattern: String, zone: ZoneId = ZoneId.systemDefault()): String {
    val formatter = DateTimeFormatter.ofPattern(pattern).withZone(zone)
    return formatter.format(this)
}