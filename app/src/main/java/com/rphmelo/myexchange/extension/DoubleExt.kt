package com.rphmelo.myexchange.extension

import java.math.RoundingMode

fun Double.convertCurrency(ratio: Double, rateNewValue: Double): Double {
    return (ratio * rateNewValue).round(4)
}

fun calculateRatio(rateListValue: Double, rateInputValue: Double): Double{
    return (rateListValue / rateInputValue).round(4)
}

fun Double.round(decimals: Int): Double {
    return this.toBigDecimal().setScale(decimals, RoundingMode.UP).toDouble()
}