package com.example.project2.screens.axxonOne.camera

import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.structure.axxonOne.cameraInfo.Camera
import com.example.project2.utils.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CameraModel @Inject constructor(
    private val serverRepository: ServerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state: StateFlow<CameraState> get() = _state

    fun init(camera: Camera) {
        _state.value = _state.value.copy(name = camera.displayName)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val videoStream = camera.videoStreams.firstOrNull()?.accessPoint
                    ?: throw Exception("No video stream available")
                val bytes = serverRepository.getSnapshot(videoStream)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                withContext(Dispatchers.Main) {
                    _state.value = _state.value.copy(bitmap = bitmap)
                }
            } catch (e: Exception) {
                Log.e("CameraModel", "Error loading image: $e")
            }
        }
    }
}
