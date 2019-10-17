package com.rphmelo.myexchange.rates.repository

import com.rphmelo.myexchange.rates.model.RatesResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatesRepository  @Inject
constructor(private val ratesService: RatesService) {

    fun getLatest(base: String): Observable<RatesResponse> {
        return ratesService.getLatest(
            base
        )
    }
}