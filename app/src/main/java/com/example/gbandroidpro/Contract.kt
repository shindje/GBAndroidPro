package com.example.gbandroidpro

import com.example.gbandroidpro.view.AppState

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}

interface Repository<T> {
    suspend fun getData(word: String): T
}

interface RepositoryLocal<T>: Repository<T> {
    suspend fun setData(appState: AppState)
    suspend fun getFavourites(): T
    suspend fun setFavourite(word: String, description: String?)
    suspend fun removeFavourite(word: String)
    suspend fun isFavourite(word: String): Boolean
}

interface DataSource<T> {
    suspend fun getData(word: String): T
}

interface DataSourceLocal<T>: DataSource<T> {
    suspend fun setData(appState: AppState)
    suspend fun getFavourites(): T
    suspend fun setFavourite(word: String, description: String?)
    suspend fun removeFavourite(word: String)
    suspend fun isFavourite(word: String): Boolean
}