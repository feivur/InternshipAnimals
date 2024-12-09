package com.example.project2.screens.camera

import com.example.project2.screens.axxonOne.data.ServerDataState
import com.example.project2.structure.axxonOne.Camera
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//todo
class CameraModel {
    private val _state = MutableStateFlow(CameraState())
    val state: StateFlow<CameraState> get() = _state


    fun init(camera: Camera){
        //todo
    }

    fun loadImage(){
        //todo здесь мы получим bitmap
    }
}