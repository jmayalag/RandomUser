package com.incursio.randomusers

import android.app.Application
import com.incursio.randomusers.repository.remote.RetrofitFactory
import timber.log.Timber

open class RandomUserApp : Application() {
    companion object {
        val randomUsersService = RetrofitFactory.createRandomUsersService()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Application created")
        }
    }
}