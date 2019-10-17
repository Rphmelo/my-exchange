package com.rphmelo.myexchange.di.components

import android.app.Application
import com.rphmelo.myexchange.MyApplication
import com.rphmelo.myexchange.di.modules.ActivityModule
import com.rphmelo.myexchange.di.modules.MyExchangeModule
import com.rphmelo.myexchange.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    MyExchangeModule::class,
    ActivityModule::class,
    ViewModelModule::class
])
interface MyExchangeComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): MyExchangeComponent
    }

    fun inject(app: MyApplication)
}