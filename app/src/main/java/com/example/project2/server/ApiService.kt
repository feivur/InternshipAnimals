package com.example.project2.server

import com.example.project2.server.version.ServerVersionResponse
import com.example.project2.structure.axxonOne.CameraList
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
    ): retrofit2.Response<String>
}


//todo /camerа/list - Получить картинку с камеры через access point  и плагин Gson to kotlin -  вывести ее под версией сервера
// https://docs.itvgroup.ru/confluence/pages/viewpage.action?pageId=198799226
// https://plugins.jetbrains.com/plugin/9960-json-to-kotlin-class-jsontokotlinclass-
// https://docs.itvgroup.ru/confluence/pages/viewpage.action?pageId=198799219

