package com.rphmelo.myexchange.rates.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RatesResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: HashMap<String, Double>
): Serializable