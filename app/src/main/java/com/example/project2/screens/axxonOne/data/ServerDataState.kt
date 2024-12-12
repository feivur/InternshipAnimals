package com.example.project2.screens.axxonOne.data

import com.example.project2.structure.axxonOne.cameraInfo.CameraWithSnapshot

data class ServerDataState(
    val version: String = "",
    val cameras: List<CameraWithSnapshot> = emptyList()
)
