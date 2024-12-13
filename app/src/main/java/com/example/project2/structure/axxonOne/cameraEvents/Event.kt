package com.example.project2.structure.axxonOne.cameraEvents

data class Event(
    val id: String,
    val type: String,
    val timestamp: String,
    val source: String,
    val origin: String,
    val duration: String?,
    val rectangles: List<Rectangle>,


    val alertState: String? = null,
    val multiPhaseSyncId: String? = null,
    val extra: List<Extra>? = null
)
