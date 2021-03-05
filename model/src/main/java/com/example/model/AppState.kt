package com.example.model

sealed class AppState {

    data class Success(val data: List<com.example.model.DataModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}