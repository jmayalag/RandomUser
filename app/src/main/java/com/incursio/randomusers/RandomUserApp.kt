package com.incursio.randomusers

import android.app.Application
import timber.log.Timber

open class RandomUserApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Application created")
        }
    }
}