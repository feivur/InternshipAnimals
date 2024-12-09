package com.example.project2.utils

import android.util.Log
import com.example.project2.server.RetrofitInstance
import com.example.project2.structure.axxonOne.Camera
import com.example.project2.structure.axxonOne.CameraWithSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


object ServerRepository {

    val cameraName = "Camera On Stairs"//todo

    suspend fun fetchCamerasWithSnapshots(): List<CameraWithSnapshot> {
        val response = RetrofitInstance.api.getCameras()
        val cameras = response.cameras

        val camerasWithSnapshots = cameras.map { camera ->
            val snapshotUrl = getSnapshotUrlForCamera(camera)
            CameraWithSnapshot(camera = camera, snapshotUrl = snapshotUrl)
        }

        return camerasWithSnapshots
    }

    private suspend fun getSnapshotUrlForCamera(camera: Camera): String? {
        return try {
            // получаем snapshotURL для каждой камеры
            val snapshotResponse = RetrofitInstance.api.getSnapshot(camera.displayId)
            if (snapshotResponse.isSuccessful) {
                val baseUrl = snapshotResponse.body()
                baseUrl?.let {
                    // убираем hosts/
                    val snapshotUrl = "http://try.axxonsoft.com:8000/asip-api/$it"
                    snapshotUrl.replace("hosts/", "") // убираем hosts/  из accessPoint
                }
            } else {
                Log.e("SnapshotError", "Error fetching snapshot URL: ${snapshotResponse.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("SnapshotError", "Error fetching snapshot URL for camera ${camera.displayId}: $e")
            null
        }
    }

    fun fetchServerVersion(): Flow<String> = flow {
        val response = RetrofitInstance.api.getServerVersion()
        emit(response.version)
    }
}
