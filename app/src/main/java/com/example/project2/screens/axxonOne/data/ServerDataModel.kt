package com.example.project2.screens.axxonOne.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.utils.ServerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class ServerDataModel : ViewModel() {

    private val _serverDataState = MutableStateFlow(ServerDataState())
    val serverDataState: StateFlow<ServerDataState> get() = _serverDataState

    fun loadServerVersion() {
        viewModelScope.launch {
            ServerRepository.fetchServerVersion().collect { version ->
                // Обновляем состояние с новой версией сервера
                _serverDataState.value = _serverDataState.value.copy(version = version)
            }
        }
    }

    fun loadCameras() {
        viewModelScope.launch {
            ServerRepository.fetchCameras()
                .catch {
                    // Если произошла ошибка, обновляем состояние с пустыми камерами
                    _serverDataState.value = _serverDataState.value.copy(cameras = emptyList())
                }
                .collect { serverData ->
                    // Обновляем состояние с камерами и снимками
                    _serverDataState.value = serverData
                }
        }
    }
}
