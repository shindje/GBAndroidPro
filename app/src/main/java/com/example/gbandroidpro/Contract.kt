package com.example.gbandroidpro

import io.reactivex.Observable

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}

interface Repository<T> {
    suspend fun getData(word: String): T
}

interface DataSource<T> {
    suspend fun getData(word: String): T
}