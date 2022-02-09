package com.example.demoforajob.app

import android.app.Application
import com.example.demoforajob.BuildConfig
import timber.log.Timber

class MyAppLication : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        lateinit var application: Application
    }
}