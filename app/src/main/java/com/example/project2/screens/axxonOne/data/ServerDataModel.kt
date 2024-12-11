package com.example.project2.screens.axxonOne.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.structure.axxonOne.Camera
import com.example.project2.structure.axxonOne.CameraWithSnapshot
import com.example.project2.utils.ServerRepository
import kotlinx.coroutines.Dispatchers
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
                cameras.addAll(ServerRepository.getCameras())
                val camerasWithSnapshots = cameras.map { camera ->
                    val snapshotUrl = camera.videoStreams.firstOrNull()?.accessPoint
                    CameraWithSnapshot(camera, snapshotUrl)
                }
                _serverDataState.value = _serverDataState.value.copy(cameras = camerasWithSnapshots)
            } catch (e: Exception) {
                Log.e("ServerDataModel", "Error loading cameras: $e")
            }
        }
    }
}


//todo сделать экран со списком камер
// каждая камера самостоятельна и грузит свою картинку своей моделью
// todo fun loadCameras() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val cameras = ServerRepository.getCameras()
//            val camera1 = cameras.first()
//            try {
//                val videoSourceId = camera1.videoStreams.first().accessPoint
//                val bytes = ServerRepository.getSnapshot(videoSourceId) // todo move to CameraMovel
//                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                //todo передать в UI
//            }catch (e: Exception){
//                //todo  передать в UI
//            }
//        }


//    fun loadCamerasOld() {
//        viewModelScope.launch {
//            try {
//                val camerasWithSnapshots = ServerRepository.fetchCamerasWithSnapshots()
//                _serverDataState.value = _serverDataState.value.copy(
//                    cameras = camerasWithSnapshots,
//                    cameraCount = camerasWithSnapshots.size
//                )
//            } catch (e: Exception) {
//                _serverDataState.value = _serverDataState.value.copy(
//                    cameras = emptyList(),
//                    cameraCount = 0
//                )
//                Log.e("LoadCamerasError", "Error loading cameras: $e")
//            }
//        }
//    }
//
