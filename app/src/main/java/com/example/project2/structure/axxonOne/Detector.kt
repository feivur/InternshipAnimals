package com.example.project2.structure.axxonOne

data class Detector(
    val accessPoint: String,
    val displayName: String,
    val events: List<String>,
    val isActivated: Boolean,
    val parentDetector: String,
    val type: String
)