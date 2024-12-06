package com.example.project2.screens.axxonOne.version
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.server.ServerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServerVersionModel : ViewModel() {

    private val _serverVersion = MutableStateFlow("Loading...")
    val serverVersion: StateFlow<String> get() = _serverVersion

    fun loadServerVersion() {
        viewModelScope.launch {
            ServerRepository.fetchServerVersion().collect { version ->
                _serverVersion.value = version
            }
        }
    }
}

