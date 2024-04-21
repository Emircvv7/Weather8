package com.example.weather2

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val data: Any) : UiState()
    data class Error(val error: String) : UiState()
}