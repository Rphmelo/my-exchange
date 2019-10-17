package com.rphmelo.myexchange.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rphmelo.myexchange.di.keys.ViewModelKey
import com.rphmelo.myexchange.rates.ui.RatesViewModel
import com.rphmelo.myexchange.util.FactoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RatesViewModel::class)
    abstract fun bindRepoViewModel(repoViewModel: RatesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: FactoryViewModel): ViewModelProvider.Factory
}