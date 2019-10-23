package com.rphmelo.myexchange.extension

import java.math.RoundingMode

fun Double.convertCurrency(rateOldValue: Double, rateNewValue: Double): Double {
    val ratio = this / rateOldValue
    return (ratio * rateNewValue).round(4)
}

fun Double.round(decimals: Int): Double {
    return this.toBigDecimal().setScale(decimals, RoundingMode.UP).toDouble()
}