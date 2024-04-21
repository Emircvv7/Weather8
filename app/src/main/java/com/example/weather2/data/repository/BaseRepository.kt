package com.example.weather2.data.repository

import com.example.weather2.Resource

open class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(apiCall.invoke())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}
