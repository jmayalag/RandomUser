package com.incursio.randomusers.repository.local

import com.incursio.randomusers.repository.Result
import com.incursio.randomusers.repository.Result.Error
import com.incursio.randomusers.repository.Result.Success
import com.incursio.randomusers.repository.db.UsersDao
import com.incursio.randomusers.repository.remote.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UsersLocalDataSource(
    private val usersDao: UsersDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getUsers(): Result<List<User>> = withContext(ioDispatcher) {
        try {
            Success(usersDao.getUsers())
        } catch (e: Exception) {
            Error(e)
        }
    }

    suspend fun getUser(uuid: String): Result<User> = withContext(ioDispatcher) {
        try {
            val user = usersDao.getUserByUUID(uuid)
                ?: return@withContext Error(Exception("User not found"))

            Success(user)
        } catch (e: Exception) {
            Error(e)
        }
    }

    suspend fun findUsers(term: String): Result<List<User>> = withContext(ioDispatcher) {
        try {
            Success(usersDao.findUsers(term))
        } catch (e: Exception) {
            Error(e)
        }
    }

    suspend fun saveUser(user: User) = withContext(ioDispatcher) {
        usersDao.insertUser(user)
    }

    suspend fun saveUsers(users: List<User>) = withContext(ioDispatcher) {
        Timber.v("Saving ${users.size} users to db")
        usersDao.insertUsers(users)
    }

    suspend fun updateUser(uuid: String, isSaved: Boolean) = withContext(ioDispatcher) {
        usersDao.updateSavedUser(uuid, isSaved)
    }

    suspend fun deleteAllUsers() = withContext(ioDispatcher) {
        usersDao.deleteAllUsers()
    }

    suspend fun clearSavedUsers() = withContext(ioDispatcher) {
        usersDao.clearSavedUsers()
    }

    /*** Convenience Methods ***/
    suspend fun updateUser(user: User, isSaved: Boolean) = updateUser(user.login.uuid, isSaved)
}