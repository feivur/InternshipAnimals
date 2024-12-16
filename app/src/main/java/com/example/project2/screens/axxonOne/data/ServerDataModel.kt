package com.example.project2.screens.axxonOne.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.structure.axxonOne.cameraInfo.Camera
import com.example.project2.structure.axxonOne.cameraInfo.CameraWithSnapshot
import com.example.project2.utils.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ServerDataModel @Inject constructor(
    private val serverRepository: ServerRepository
) : ViewModel() {

    private val _serverDataState = MutableStateFlow(ServerDataState())
    val serverDataState: StateFlow<ServerDataState> get() = _serverDataState

    fun loadServerVersion() {
        viewModelScope.launch(Dispatchers.IO) {
            serverRepository.fetchServerVersion().collect { version ->
                withContext(Dispatchers.Main) {
                    _serverDataState.value = _serverDataState.value.copy(version = version)
                }
            }
        }
    }

    private val cameras: MutableList<Camera> = mutableListOf()

    fun getCamera(cameraId: String): Camera {
        return cameras
            .firstOrNull { it.id() == cameraId }
            ?: throw RuntimeException("Camera id not found $cameraId")
    }

    fun loadCameras() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                cameras.clear()
                cameras.addAll(serverRepository.getCameras())

                val camerasWithSnapshots = cameras.map { camera ->
                    val snapshotUrl = camera.videoStreams.firstOrNull()?.accessPoint
                    CameraWithSnapshot(camera, snapshotUrl)
                }

                withContext(Dispatchers.Main) {
                    _serverDataState.value =
                        _serverDataState.value.copy(cameras = camerasWithSnapshots)
                }
            } catch (e: Exception) {
                Log.e("ServerDataModel", "Error loading cameras: $e")
            }
        }
    }
}
