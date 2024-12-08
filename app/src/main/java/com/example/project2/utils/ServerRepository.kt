package com.example.project2.utils

import android.util.Log
import com.example.project2.screens.axxonOne.data.ServerDataState
import com.example.project2.server.RetrofitInstance
import com.example.project2.structure.axxonOne.CameraWithSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


object ServerRepository {

    fun fetchServerVersion(): Flow<String> = flow {
        try {
            val response = RetrofitInstance.api.getServerVersion()
            if (response.version.isNotBlank()) {
                emit(response.version)
            } else {
                throw RuntimeException("Server version not found")
            }
        } catch (e: Exception) {
            // Логируем ошибку
            Log.e("ServerRepository", "Error fetching server version: ${e.message}", e)
            emit("Error")
        }
    }


    fun fetchCameras(): Flow<ServerDataState> = flow {
        try {
            val response = RetrofitInstance.api.getCameras()
            if (response.cameras.isNotEmpty()) {
                val camerasWithSnapshot = response.cameras.map { camera ->
                    // Получаем ссылку на снимок для каждой камеры
                    val snapshotUrl =
                        camera.videoStreams.firstOrNull()?.accessPoint?.let { accessPoint ->
                            val videoSourceId = accessPoint.split("/").drop(1).joinToString("/")
                            val snapshotResponse = RetrofitInstance.api.getSnapshot(videoSourceId)

                            // Если снимок был успешно получен, извлекаем URL
                            if (snapshotResponse.isSuccessful) {
                                snapshotResponse.body()
                            } else {
                                null
                            }
                        }

                    // Создаем объект CameraWithSnapshot с камерой и снимком
                    CameraWithSnapshot(camera = camera, snapshotUrl = snapshotUrl)
                }

                // Эмитим обновленное состояние с версией сервера и камерами
                emit(ServerDataState(version = "1.0", cameras = camerasWithSnapshot))
            } else {
                throw RuntimeException("No cameras found")
            }
        } catch (e: Exception) {
            // Если произошла ошибка, эмитим состояние с пустыми данными
            emit(ServerDataState(version = "Error", cameras = emptyList()))
            throw RuntimeException("Error fetching cameras: ${e.localizedMessage}")
        }
    }
}