package com.rphmelo.myexchange.extension

import java.math.RoundingMode

fun Double.convertCurrency(ratio: Double): Double {
    return (ratio.round(4) * this.round(4)).round(4)
}

fun calculateRatio(rateListValue: Double, rateInputValue: Double): Double{
    return (rateListValue.round(4) / rateInputValue.round(4)).round(4)
}

fun Double.round(decimals: Int): Double {
    return this.toBigDecimal().setScale(decimals, RoundingMode.UP).toDouble()
}