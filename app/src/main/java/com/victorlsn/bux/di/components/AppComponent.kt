package com.victorlsn.bux.di.components

import android.app.Application
import com.victorlsn.bux.application.App
import com.victorlsn.bux.di.modules.ActivityBindingModule
import com.victorlsn.bux.di.modules.ApiModule
import com.victorlsn.bux.di.modules.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ActivityBindingModule::class,
        ApiModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun apiModule(apiModule: ApiModule): Builder

        fun build(): AppComponent
    }
}