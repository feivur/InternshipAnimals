package com.example.project2.server

import com.example.project2.server.version.ServerVersionResponse
import com.example.project2.structure.axxonOne.cameraInfo.CameraList
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("product/version/")
    suspend fun getServerVersion(): ServerVersionResponse

    @GET("camera/list")
    suspend fun getCameras(
        @Query("limit") limit: Int = 1000,
        @Query("filter") filter: String? = null
    ): CameraList

    @GET("live/media/snapshot/{videoSourceId}")
    suspend fun getSnapshot(
        @Path("videoSourceId") videoSourceId: String,
        @Query("w") width: Int? = null,
        @Query("h") height: Int? = null
    ): retrofit2.Response<ResponseBody>

    //todo получить список событий детекторов
    // http://136.243.144.109:8000/asip-api/archive/events/detectors/20241212T111205.843/20241211T105705.843?limit=20&offset=0&type=&limit_to_archive=1
}


