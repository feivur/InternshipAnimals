package com.example.project2.screens.axxonOne.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.utils.ServerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServerDataModel : ViewModel() {

    private val _serverDataState = MutableStateFlow(ServerDataState())
    val serverDataState: StateFlow<ServerDataState> get() = _serverDataState

    fun loadServerVersion() {
        viewModelScope.launch {
            ServerRepository.fetchServerVersion().collect { version ->
                _serverDataState.value = _serverDataState.value.copy(version = version)
            }
        }
    }

    fun loadCameras() {
        viewModelScope.launch {
            try {
                val camerasWithSnapshots = ServerRepository.fetchCamerasWithSnapshots()
                _serverDataState.value = _serverDataState.value.copy(
                    cameras = camerasWithSnapshots,
                    cameraCount = camerasWithSnapshots.size
                )
            } catch (e: Exception) {
                _serverDataState.value = _serverDataState.value.copy(
                    cameras = emptyList(),
                    cameraCount = 0
                )
                Log.e("LoadCamerasError", "Error loading cameras: $e")
            }
        }
    }
}