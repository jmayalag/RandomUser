package com.incursio.randomusers

import android.app.Application
import com.incursio.randomusers.repository.remote.RandomUsersService
import com.incursio.randomusers.repository.remote.RetrofitFactory
import com.incursio.randomusers.repository.remote.UsersRepository
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class RandomUserApp : Application() {
    lateinit var usersRepository: UsersRepository
        private set

    lateinit var picasso: Picasso
        private set

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Application created")
        }

        setupDependencies()
    }

    private fun setupDependencies() {
        val logging =
            if (BuildConfig.DEBUG) RetrofitFactory.provideLoggingInterceptor()
            else null

        val okHttpClient = RetrofitFactory.providerOkHttpClient(logging)
        val converterFactory = MoshiConverterFactory.create()

        val usersService =
            RetrofitFactory.provideRandomUsersService(okHttpClient, converterFactory)

        usersRepository = UsersRepository(usersService)

        picasso = Picasso.Builder(this)
            .downloader(OkHttp3Downloader(okHttpClient))
            .listener { _, uri, exception ->
                Timber.e(exception, "Failed to load image: %s", uri)
            }
            .build()

        Picasso.setSingletonInstance(picasso)
    }
}