package com.example.project2.server

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("product/version/")
    fun getServerVersion(): Call<ServerVersionResponse>
}