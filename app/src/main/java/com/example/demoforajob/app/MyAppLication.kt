package com.example.demoforajob.app

import android.app.Application
import com.example.demoforajob.BuildConfig
import com.example.demoforajob.data.source.local.room.ApplicationDatabase
import timber.log.Timber

class MyAppLication : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        ApplicationDatabase.init(this)
    }

    companion object {
        lateinit var application: Application
    }
}