package com.incursio.randomusers.repository.remote

import com.incursio.randomusers.repository.remote.model.User
import timber.log.Timber

class UsersRepository(private val service: RandomUsersService) {
    suspend fun getUsers(forceUpdate: Boolean): List<User> {
        if (!forceUpdate) {
            Timber.d("get users from cache")
        }

        return service.listUsers().results
    }
}