package com.alness.myapplication.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alness.myapplication.api.GenericRepository
import com.alness.myapplication.models.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repo: GenericRepository
): ViewModel() {
    private val _user = MutableLiveData<UserResponse?>(null)
    val user: LiveData<UserResponse?> = _user

    fun loadUser(id: String) {
        viewModelScope.launch {
            try {
                val user = repo.get("/users/$id", clazz = UserResponse::class.java) as? UserResponse
                _user.postValue(user)
            } catch (e: Exception) {
                _user.postValue(null)
            }
        }
    }
}