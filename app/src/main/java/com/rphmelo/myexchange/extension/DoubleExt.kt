package com.rphmelo.myexchange.extension

fun Double.convertCurrency(rateOldValue: Double, rateNewValue: Double): Double {
    val ratio = this / rateOldValue
    return ratio * rateNewValue
}