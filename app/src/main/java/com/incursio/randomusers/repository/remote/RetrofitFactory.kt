package com.incursio.randomusers.repository.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    private const val BASE_URL = "https://randomuser.me/api/"

    fun provideRandomUsersService(
        okHttpClient: OkHttpClient, converterFactory: Converter.Factory
    ): RandomUsersService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(RandomUsersService::class.java)
    }

    fun providerOkHttpClient(loggingInterceptor: Interceptor?): OkHttpClient {
        return OkHttpClient.Builder()
            .apply { if (loggingInterceptor != null) addInterceptor(loggingInterceptor) }
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BASIC }
    }
}