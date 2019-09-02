package com.incursio.randomusers.users

import androidx.lifecycle.*
import com.incursio.randomusers.repository.Result
import com.incursio.randomusers.repository.Result.Success
import com.incursio.randomusers.repository.remote.UsersRepository
import com.incursio.randomusers.repository.remote.model.User
import kotlinx.coroutines.launch
import timber.log.Timber

class UsersViewModel(private val repository: UsersRepository) : ViewModel() {
    private val _users = MutableLiveData<List<User>>().apply { value = emptyList() }
    val users: LiveData<List<User>> = _users

    private val _favUsers = MutableLiveData<List<User>>().apply { value = emptyList() }
    val favUsers: LiveData<List<User>> = _favUsers

    val hasNoUsers: LiveData<Boolean> = Transformations.map(users) {
        it.isEmpty()
    }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _selectedUser = MutableLiveData<User?>().apply { value = null }
    val openUserEvent: LiveData<User?> = _selectedUser

    init {
//        loadUsers(false)
    }

    fun openUser(user: User) {
        _selectedUser.value = user
    }

    fun openedUser() {
        _selectedUser.value = null
    }

    fun loadUsers(forceUpdate: Boolean) {
        viewModelScope.launch {
            val favResult = repository.getFavUsers()

            with(favResult) {
                when (this) {
                    is Result.Error -> {
                        Timber.w(exception, "Error getting fav users")
                        _favUsers.value = emptyList()
                    }
                    is Success -> {
                        Timber.d("Got Fav users ${data.joinToString(", ") { it.fullName }}")
                        _favUsers.value = data
                    }
                }
            }
        }

        _dataLoading.value = true
        viewModelScope.launch {

            val result = repository.getUsers(forceUpdate)

            if (result is Success) {
                val users = result.data
                _users.value = users
            } else {
                _users.value = emptyList()
            }

            _dataLoading.value = false
        }
    }

    fun refresh() {
        loadUsers(true)
    }
}