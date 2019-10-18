package com.rphmelo.myexchange.rates.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RatesResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: HashMap<String, Double>?
): Serializable {

    fun getRatesList(): List<Rate>{
        val rateList: MutableList<Rate> = mutableListOf()

        rates?.toList()?.sortedBy { it.first }?.map { rateList.add(Rate(it.first, it.second)) }

        return rateList
    }
}