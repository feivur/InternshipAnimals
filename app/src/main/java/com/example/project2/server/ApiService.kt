package com.example.project2.server

import com.example.project2.server.version.ServerVersionResponse
import retrofit2.http.GET

interface ApiService {
    @GET("product/version/")
    suspend fun getServerVersion(): ServerVersionResponse

    //todo /camerа/list - Получить картинку с камеры через access point  и плагин Gson to kotlin
    // https://docs.itvgroup.ru/confluence/pages/viewpage.action?pageId=198799226
    // https://plugins.jetbrains.com/plugin/9960-json-to-kotlin-class-jsontokotlinclass-
    // https://docs.itvgroup.ru/confluence/pages/viewpage.action?pageId=198799219
}
