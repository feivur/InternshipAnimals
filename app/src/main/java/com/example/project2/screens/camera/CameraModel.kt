package com.example.project2.screens.camera

import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.structure.axxonOne.Camera
import com.example.project2.utils.ServerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//todo
class CameraModel : ViewModel() {
    private val _state = MutableStateFlow(CameraState())
    val state: StateFlow<CameraState> get() = _state

    fun init(camera: Camera) {
        _state.value = _state.value.copy(name = camera.displayName)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val videoStream = camera.videoStreams.firstOrNull()?.accessPoint
                    ?: throw Exception("No video stream available")
                val bytes = ServerRepository.getSnapshot(videoStream)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                _state.value = _state.value.copy(bitmap = bitmap)
            } catch (e: Exception) {
                Log.e("CameraModel", "Error loading image: $e")
            }
        }
    }
}
