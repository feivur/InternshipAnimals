package com.example.project2.server

import retrofit2.http.GET

interface ApiService {
    @GET("product/version/")
    suspend fun getServerVersion(): ServerVersionResponse // flow
}
