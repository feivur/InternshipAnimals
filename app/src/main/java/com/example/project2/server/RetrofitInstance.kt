package com.example.project2.server

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {

    private fun retryInterceptor(retryCount: Int = 3): Interceptor {
        return Interceptor { chain ->
            var attempt = 0
            var response: okhttp3.Response
            do {
                response = chain.proceed(chain.request())
                attempt++
            } while (!response.isSuccessful && attempt < retryCount)
            response
        }
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(retryInterceptor())
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Authorization", Credentials.basic("root", "root"))
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://try.axxonsoft.com:8000/asip-api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}
