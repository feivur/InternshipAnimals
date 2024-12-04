package com.example.project2.server

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerVersionModel : ViewModel() {
    var serverVersion = mutableStateOf("Loading...")
        private set

    fun fetchServerVersion() {
        RetrofitInstance.api.getServerVersion().enqueue(object : Callback<ServerVersionResponse> {
            override fun onResponse(
                call: Call<ServerVersionResponse>,
                response: Response<ServerVersionResponse>
            ) {
                if (response.isSuccessful) {
                    serverVersion.value = response.body()?.version ?: "Unknown Version"
                } else {
                    serverVersion.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ServerVersionResponse>, t: Throwable) {
                serverVersion.value = "Error: ${t.message}"
            }
        })
    }
}
