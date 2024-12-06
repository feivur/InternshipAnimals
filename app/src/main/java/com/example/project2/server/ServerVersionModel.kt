package com.example.project2.server

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ServerVersionModel : ViewModel() {

    private val _serverVersion = MutableStateFlow("Loading...")
    val serverVersion: StateFlow<String> get() = _serverVersion

    fun loadServerVersion() {
        ServerRepository
            .fetchServerVersion()
            .onEach {  _serverVersion.value = it }
            .catch { emit("Error: ${it.message}") }
            .launchIn(viewModelScope)
    }
}

