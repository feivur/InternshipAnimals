package com.example.project2.utils

import android.util.Log
import com.example.project2.server.ApiService
import com.example.project2.server.RetrofitInstance
import com.example.project2.structure.axxonOne.cameraEvents.Event
import com.example.project2.structure.axxonOne.cameraInfo.Camera
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerRepository @Inject constructor(//todo?
    private val apiService: ApiService
) {

    //todo
    suspend fun getEvents(
        endTime: String,
        beginTime: String,
        limit: Int,
        offset: Int,
        limitToArchive: Int
    ): List<Event> {
        return RetrofitInstance.api.getEvents(
            endTime = endTime,
            beginTime = beginTime,
            limit = limit,
            offset = offset,
            type = null,
            limitToArchive = limitToArchive
        ).events
    }
    //todo


    suspend fun getCameras(): List<Camera>{
        val response = RetrofitInstance.api.getCameras()
        return response.cameras
    }

    suspend fun getSnapshot(videoSourceId: String): ByteArray {
        val cleanedId = videoSourceId.replaceFirst("hosts/", "")
        Log.d("getSnapshot", "Cleaned video source ID: $cleanedId")

        try {
            val snapshotResponse = RetrofitInstance.api.getSnapshot(cleanedId)
            if (!snapshotResponse.isSuccessful) {
                Log.e("getSnapshot", "Failed to fetch snapshot: ${snapshotResponse.message()}")
                throw RuntimeException("Failed to fetch snapshot")
            }

            val bytes = snapshotResponse.body()?.bytes() ?: byteArrayOf()
            if (bytes.isEmpty()) {
                Log.e("getSnapshot", "Received empty image data")
                throw RuntimeException("no image data")
            }

            Log.d("getSnapshot", "Successfully fetched snapshot, size: ${bytes.size} bytes")
            return bytes
        } catch (e: Exception) {
            Log.e("getSnapshot", "Error occurred: ${e.message}", e)
            throw RuntimeException("Error fetching snapshot", e)
        }
    }

    fun fetchServerVersion(): Flow<String> = flow {
        val response = RetrofitInstance.api.getServerVersion()
        emit(response.version)
    }
}