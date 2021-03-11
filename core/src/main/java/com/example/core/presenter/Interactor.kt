package com.example.core.presenter

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}