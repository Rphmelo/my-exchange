package com.rphmelo.myexchange.rates.data.repository

import com.rphmelo.myexchange.rates.domain.model.RatesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesService {
    @GET("/latest")
    fun getLatest(
        @Query("base") base: String
    ): Observable<RatesResponse>
}