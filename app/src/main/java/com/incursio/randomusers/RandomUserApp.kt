package com.incursio.randomusers

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.incursio.randomusers.repository.db.UserDatabase
import com.incursio.randomusers.repository.local.UsersLocalDataSource
import com.incursio.randomusers.repository.remote.RetrofitFactory
import com.incursio.randomusers.repository.remote.UsersRemoteDataSource
import com.incursio.randomusers.repository.remote.UsersRepository
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class RandomUserApp : Application() {
    private lateinit var dataBase: UserDatabase
    lateinit var usersRepository: UsersRepository
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

        dataBase = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java, "Users.db"
        ).build()

        val localDataSource = UsersLocalDataSource(dataBase.usersDao())
        val remoteDataSource = UsersRemoteDataSource(usersService)

        usersRepository = UsersRepository(localDataSource, remoteDataSource)
    }
}