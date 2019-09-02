package com.incursio.randomusers.repository.remote

import com.incursio.randomusers.repository.Result
import com.incursio.randomusers.repository.Result.Error
import com.incursio.randomusers.repository.Result.Success
import com.incursio.randomusers.repository.remote.model.User
import java.lang.Exception

class UsersRemoteDataSource(
    private val service: RandomUsersService
) {

    suspend fun getUsers(): Result<List<User>> {
        return try {
            Success(service.listUsers().results)
        } catch (e: Exception) {
            Error(e)
        }
    }

    @Deprecated("Should fetch from local data source only")
    suspend fun getUser(userId: String): User? {
        return service.listUsers().results.find { it.idValue == userId }
    }
}