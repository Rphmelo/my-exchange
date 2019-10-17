package com.rphmelo.myexchange.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.rphmelo.myexchange.rates.repository.RatesRepository
import com.rphmelo.myexchange.rates.repository.RatesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Named
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
    fun provideRetrofit(
        gson: Gson,
        @Named("apiUrl") apiUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(apiUrl)
            .build()
    }

    @Provides
    @Singleton
    @Named("apiUrl")
    fun provideApiURL(): String {
        return "https://revolut.duckdns.org"
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