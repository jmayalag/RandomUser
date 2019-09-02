package com.incursio.randomusers.repository.remote

import com.incursio.randomusers.repository.Result
import com.incursio.randomusers.repository.Result.*
import com.incursio.randomusers.repository.local.UsersLocalDataSource
import com.incursio.randomusers.repository.remote.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class UsersRepository(
    private val localDataSource: UsersLocalDataSource,
    private val remoteDataSource: UsersRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getUsers(forceUpdate: Boolean): Result<List<User>> {
        if (!forceUpdate) {
            Timber.d("get users from local db")
            val result = localDataSource.getUsers()
            if (result is Success && result.data.isEmpty()) {
                // If local data source is empty, must fetch from network
                Timber.d("First app load, will populate db")
                return fetchFromRemoteOrLocal(true)
            }
            return result
        }

        return fetchFromRemoteOrLocal(forceUpdate)
    }

    private suspend fun fetchFromRemoteOrLocal(forceUpdate: Boolean): Result<List<User>> {
        // Try fetching from remote
        val remoteResult = remoteDataSource.getUsers()

        with(remoteResult) {
            when (this) {
                is Error -> {
                    Timber.w(
                        exception,
                        "Remote data source fetch failed"
                    )
                }
                is Success -> {
                    Timber.v("Remote data source fetch success")
                    refreshLocalDataSource(data)
                    return this
                }
                else -> throw IllegalStateException()
            }
        }

        if (forceUpdate) {
            // Skip reading from local
            return Error(Exception("Can't force update, remote data source fetch failed"))
        }

        // Try from local if remote failed
        return with(localDataSource.getUsers()) {
            if (this is Success) this
            else Error(Exception("Error fetching from remote and local data sources"))
        }
    }

    /**
     * Get user from local storage
     *
     * The api does not support getting a single user by id,
     * hence it must be stored when the list is fetched
     */
    suspend fun getUser(userId: String): Result<User> {
        return localDataSource.getUser(userId)
    }

    private suspend fun refreshLocalDataSource(users: List<User>) {
        localDataSource.saveUsers(users)
    }

    suspend fun updateUser(userId: String, isSaved: Boolean) {
        localDataSource.updateUser(userId, isSaved)
    }
}