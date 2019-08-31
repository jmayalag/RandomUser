package com.incursio.randomusers.users

import androidx.lifecycle.*
import com.incursio.randomusers.repository.remote.UsersRepository
import com.incursio.randomusers.repository.remote.model.User
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UsersRepository) : ViewModel() {
    private val _users = MutableLiveData<List<User>>().apply { value = emptyList() }
    val users: LiveData<List<User>> = _users
    val hasNoUsers: LiveData<Boolean> = Transformations.map(users) {
        it.isEmpty()
    }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
//        loadUsers(true)
    }

    fun loadUsers(forceUpdate: Boolean) {
        viewModelScope.launch {
            _dataLoading.value = true

            val users = repository.getUsers(forceUpdate)

            _users.value = users

            _dataLoading.value = false
        }
    }

    fun refresh() {
        loadUsers(true)
    }
}