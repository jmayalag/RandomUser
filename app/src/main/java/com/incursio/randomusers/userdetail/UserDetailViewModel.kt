package com.incursio.randomusers.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incursio.randomusers.repository.remote.UsersRepository
import com.incursio.randomusers.repository.remote.model.User
import kotlinx.coroutines.launch
import timber.log.Timber

class UserDetailViewModel(private val repository: UsersRepository) : ViewModel() {

    private val _user = MutableLiveData<User?>().apply { value = null }
    val user: LiveData<User?> = _user

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isDataAvailable = MutableLiveData<Boolean>()
    val isDataAvailable: LiveData<Boolean> = _isDataAvailable

    fun start(userId: String) {
        _dataLoading.value = true
        viewModelScope.launch {
            val user = repository.getUser(userId)

            if (user != null) {
                onUserLoaded(user)
            } else {
                onDataNotAvailable(userId)
            }

            _dataLoading.value = false
        }
    }

    private fun onUserLoaded(user: User) {
        _user.value = user
        _isDataAvailable.value = true
    }

    private fun onDataNotAvailable(userId: String) {
        _user.value = null
        _isDataAvailable.value = false
        Timber.w("Data not available for userId: $userId")
    }
}