package com.example.project2.server

import retrofit2.http.GET

interface ApiService {
    @GET("product/version/")
    suspend fun getServerVersion(): ServerVersionResponse

    //todo /camerа/list
    // https://docs.itvgroup.ru/confluence/pages/viewpage.action?pageId=198799226
    // https://plugins.jetbrains.com/plugin/9960-json-to-kotlin-class-jsontokotlinclass-
    // https://docs.itvgroup.ru/confluence/pages/viewpage.action?pageId=198799219
}
