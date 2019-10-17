package com.rphmelo.myexchange.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [MyExchangeModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        @Named("apiUrl") apiUrl: String,
        okhttp: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(apiUrl)
            .client(okhttp)
            .build()
    }

    @Provides
    @Singleton
    @Named("apiUrl")
    fun provideApiURL(): String {
        return "https://revolut.duckdns.org"
    }
}