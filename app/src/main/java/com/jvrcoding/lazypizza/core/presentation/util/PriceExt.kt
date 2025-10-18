package com.jvrcoding.lazypizza.core.presentation.util

import java.math.BigDecimal

fun String.currencyToBigDecimal(): BigDecimal =
    this.replace("$", "").toBigDecimal()

fun BigDecimal.formatToCurrency(): String = "$${this}"
