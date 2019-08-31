package com.incursio.randomusers.repository.remote

import com.incursio.randomusers.repository.remote.model.ApiResult
import retrofit2.http.GET

interface RandomUsersService {
    @GET("?results=50&nat=us,dk,fr,gb")
    suspend fun listUsers(): ApiResult
}