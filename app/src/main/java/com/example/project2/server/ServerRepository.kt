package com.example.project2.server

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

object ServerRepository {

    fun fetchServerVersion(): Flow<String> = flow {
        val response = RetrofitInstance.api.getServerVersion()
        if (response.version.isNotBlank()) {
            emit(response.version)
        } else {
            emit("Error: Empty version")
        }
    }.catch { e ->
        emit("Error: ${e.message}")
    }

}