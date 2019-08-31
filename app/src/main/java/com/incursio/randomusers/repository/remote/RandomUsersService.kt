package com.incursio.randomusers.repository.remote

import com.incursio.randomusers.repository.remote.model.ApiResult
import retrofit2.Call
import retrofit2.http.GET

interface RandomUsersService {
    @GET("?results=10")
    fun listUsers(): Call<ApiResult>
}