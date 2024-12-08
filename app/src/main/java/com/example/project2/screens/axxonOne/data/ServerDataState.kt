package com.example.project2.screens.axxonOne.data

import com.example.project2.structure.axxonOne.CameraWithSnapshot

data class ServerDataState(
    val version: String = "Loading...",
    val cameras: List<CameraWithSnapshot> = emptyList()
)
