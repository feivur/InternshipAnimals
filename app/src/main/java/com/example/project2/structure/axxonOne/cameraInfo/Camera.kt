package com.example.project2.structure.axxonOne.cameraInfo

data class Camera(
    val archives: List<Archive>,
    val audioStreams: List<AudioStream>,
    val azimuth: String,
    val comment: String,
    val detectors: List<Detector>,
    val displayId: String,
    val displayName: String,
    val groups: List<String>,
    val ipAddress: String,
    val isActivated: Boolean,
    val latitude: String,
    val longitude: String,
    val model: String,
    val offlineDetectors: List<Any>,
    val ptzs: List<Any>,
    val textSources: List<Any>,
    val vendor: String,
    val videoStreams: List<VideoStream>
){
    fun id() = videoStreams.first().accessPoint
}