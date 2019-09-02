package com.incursio.randomusers.repository.remote

import com.incursio.randomusers.repository.remote.model.User
import timber.log.Timber

class UsersRepository(private val service: RandomUsersService) {
    suspend fun getUsers(forceUpdate: Boolean): List<User> {
        if (!forceUpdate) {
            // TODO: cache user
            Timber.d("get users from cache")
        }

        return service.listUsers().results
    }

    /**
     * Get user from local storage
     *
     * The api does not support getting a single user by id,
     * hence it must be stored when the list is fetched
     */
    suspend fun getUser(userId: String): User? {
        return service.listUsers().results.find { it.idValue == userId }
    }
}