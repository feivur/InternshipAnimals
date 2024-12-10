package com.example.project2.utils

import com.example.project2.server.RetrofitInstance
import com.example.project2.structure.axxonOne.Camera
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


object ServerRepository {

    suspend fun getCameras(): List<Camera>{
        val response = RetrofitInstance.api.getCameras()
        return response.cameras
    }

    suspend fun getSnapshot(videoSourceId: String): ByteArray {
        val cleanedId = videoSourceId.replaceFirst("hosts/", "")
        val response = RetrofitInstance.api.getSnapshot(cleanedId)
        return response.body()?.bytes() ?: throw RuntimeException("No image data")
    }

    fun fetchServerVersion(): Flow<String> = flow {
        val response = RetrofitInstance.api.getServerVersion()
        emit(response.version)
    }
}


//    suspend fun fetchCamerasWithSnapshots(): List<CameraWithSnapshot> {
//        val response = RetrofitInstance.api.getCameras()
//        val cameras = response.cameras
//
//        val camerasWithSnapshots = cameras.map { camera ->
//            val snapshotUrl = getSnapshotUrlForCamera(camera)
//            CameraWithSnapshot(camera = camera, snapshotUrl = snapshotUrl)
//        }
//
//        return camerasWithSnapshots
//    }

//
//    suspend fun getSnapshot(videoSourseId: String): ByteArray{
//        val vsiFixed = videoSourseId.apply { replaceFirst("hosts/", "") }
//        val snapshotResponse = RetrofitInstance.api.getSnapshot(vsiFixed)
//        val bytes = snapshotResponse.body()?.bytes() ?: byteArrayOf()
//        if (bytes.isEmpty())
//            throw RuntimeException("no image data")
//        return bytes
//    }

//    private suspend fun getSnapshotUrlForCamera(camera: Camera): String? {
//        return try {
//            // получаем snapshotURL для каждой камеры
//            val snapshotResponse = RetrofitInstance.api.getSnapshot(camera.displayId)
//            if (snapshotResponse.isSuccessful) {
//                val baseUrl = snapshotResponse.body()
//                baseUrl?.let {
//                    val snapshotUrl = "http://try.axxonsoft.com:8000/asip-api/$it"
//                    snapshotUrl.replace("hosts/", "") // убираем hosts/  из accessPoint
//                }
//            } else {
//                Log.e("SnapshotError", "Error fetching snapshot URL: ${snapshotResponse.message()}")
//                null
//            }
//        } catch (e: Exception) {
//            Log.e("SnapshotError", "Error fetching snapshot URL for camera ${camera.displayId}: $e")
//            null
//        }
//    }