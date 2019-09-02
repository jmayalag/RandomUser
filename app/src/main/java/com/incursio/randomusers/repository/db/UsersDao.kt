package com.incursio.randomusers.repository.db

import androidx.room.*
import com.incursio.randomusers.repository.remote.model.User

/**
 * Data Access Object for User
 */
@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM users WHERE uuid = :uuid")
    suspend fun getUserByUUID(uuid: String): User?

    @Query("SELECT * FROM users WHERE name_first LIKE :term")
    suspend fun findUsers(term: String): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(users: List<User>)

    @Query("UPDATE users SET isSaved = :isSaved WHERE uuid = :uuid")
    suspend fun updateSavedUser(uuid: String, isSaved: Boolean)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("UPDATE users SET isSaved = 0 WHERE isSaved = 1")
    suspend fun clearSavedUsers()
}