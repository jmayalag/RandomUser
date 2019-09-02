package com.incursio.randomusers.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.incursio.randomusers.repository.remote.model.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class UserDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}