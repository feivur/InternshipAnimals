package com.example.project2.server

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    //чтобы пытаться много раз
    private fun retryInterceptor(retryCount: Int = 3): Interceptor {
        return Interceptor { chain ->
            var attempt = 0//счетчик попыток
            var response: okhttp3.Response
            do {
                response = chain.proceed(chain.request())
                attempt++
            } while (!response.isSuccessful && attempt < retryCount)//повтор при ошибке
            response
        }
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
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
        .baseUrl("http://try.itvgroup.ru:8000")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}
