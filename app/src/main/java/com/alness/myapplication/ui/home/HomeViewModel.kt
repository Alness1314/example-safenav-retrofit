package com.alness.myapplication.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alness.myapplication.api.GenericRepository
import com.alness.myapplication.models.ModuleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: GenericRepository
) : ViewModel() {

    private val _modules = MutableLiveData<List<ModuleResponse>>()
    val modules: LiveData<List<ModuleResponse>> = _modules

    private val _uiState = MutableLiveData<UiState>(UiState.Idle)
    val uiState: LiveData<UiState> = _uiState

    fun loadModules(){
        viewModelScope.launch {
            _uiState.postValue(UiState.Loading)
            try {
                val list = repo.getList("/v1/api/modules", queries = mapOf("level" to "home"), clazz = ModuleResponse::class.java)
                _modules.postValue(list)
                _uiState.postValue(UiState.Success)

            }catch (e: Exception){
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