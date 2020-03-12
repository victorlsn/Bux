package com.victorlsn.bux.application

import com.victorlsn.bux.BuildConfig
import com.victorlsn.bux.di.components.AppComponent
import com.victorlsn.bux.di.components.DaggerAppComponent
import com.victorlsn.bux.di.modules.ApiModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {
    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            // init Timber
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        appComponent = DaggerAppComponent.builder()
            .apiModule(ApiModule())
            .application(this)
            .build()

        return appComponent as AndroidInjector<out DaggerApplication>
    }

}