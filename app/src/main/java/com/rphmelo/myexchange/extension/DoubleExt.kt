package com.rphmelo.myexchange.extension

import java.math.RoundingMode

fun Double.convertCurrency(rateOldValue: Double, rateNewValue: Double): Double {
    var ratio = 0.0

    if(this > 0 && rateOldValue > 0){
        ratio = this / rateOldValue
    }
    return (ratio * rateNewValue).round(4)
}

fun Double.round(decimals: Int): Double {
    return this.toBigDecimal().setScale(decimals, RoundingMode.UP).toDouble()
}