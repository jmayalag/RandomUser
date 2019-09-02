package com.incursio.randomusers.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incursio.randomusers.repository.Result
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

            with(repository.getUser(userId)) {
                when (this) {
                    is Result.Success -> onUserLoaded(data)
                    is Result.Error -> onDataNotAvailable(userId, exception)
                }
            }

            _dataLoading.value = false
        }
    }

    fun updateUserMarked() {
        val user = user.value ?: return

        val newValue = !user.isSaved
        Timber.d("Update user ${user.fullName} to saved: $newValue")

        viewModelScope.launch {
            repository.updateUser(user.idValue, newValue)
        }

        _user.value = user.copy(isSaved = newValue)
    }

    private fun onUserLoaded(user: User) {
        _user.value = user
        _isDataAvailable.value = true
    }

    private fun onDataNotAvailable(userId: String, cause: Exception) {
        _user.value = null
        _isDataAvailable.value = false
        Timber.w(cause, "Data not available for userId: $userId")
    }
}