package com.rphmelo.myexchange.di.modules

import com.rphmelo.myexchange.rates.ui.RatesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeRatesActivity(): RatesActivity
}