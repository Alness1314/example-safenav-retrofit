package com.alness.myapplication.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alness.myapplication.api.GenericRepository
import com.alness.myapplication.models.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import perfetto.protos.UiState
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repo: GenericRepository
) : ViewModel() {

    private val _users = MutableLiveData<List<UserResponse>>()
    val users: LiveData<List<UserResponse>> = _users

    private val _uiState = MutableLiveData<UiState>(UiState.Idle)
    val uiState: LiveData<UiState> = _uiState

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.postValue(UiState.Loading)
            try {
                val list = repo.getList("/users", clazz = UserResponse::class.java)

                _users.postValue(list)
                _uiState.postValue(UiState.Success)
            } catch (e: Exception) {
                Log.e("api exception", "Ex:", e)
                _uiState.postValue(UiState.Error(e.localizedMessage ?: "Error desconocido"))
            }
        }
    }


    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}