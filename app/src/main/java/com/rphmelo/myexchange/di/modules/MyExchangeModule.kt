package com.rphmelo.myexchange.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rphmelo.myexchange.rates.repository.RatesRepository
import com.rphmelo.myexchange.rates.repository.RatesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class MyExchangeModule {

    @Provides
    @Singleton
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRatesService(retrofit: Retrofit): RatesService {
        return retrofit.create(RatesService::class.java)
    }

    @Provides
    @Singleton
    fun provideRatesRepository(
        repoService: RatesService
    ): RatesRepository {
        return RatesRepository(repoService)
    }
}