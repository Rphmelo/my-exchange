package com.rphmelo.myexchange

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.rphmelo.myexchange.di.components.DaggerMyExchangeComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        this.initDagger()
        Stetho.initializeWithDefaults(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    private fun initDagger() {
        DaggerMyExchangeComponent.builder().application(this).build().inject(this)
    }
}