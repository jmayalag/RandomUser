package com.incursio.randomusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.incursio.randomusers.repository.remote.UsersRepository
import com.incursio.randomusers.users.UsersViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val usersRepository: UsersRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(UsersViewModel::class.java) -> UsersViewModel(
                    usersRepository
                )
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}