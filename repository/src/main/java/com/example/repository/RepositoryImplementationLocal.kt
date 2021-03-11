package com.example.repository

import com.example.gbandroidpro.DataSourceLocal
import com.example.gbandroidpro.RepositoryLocal
import com.example.model.AppState

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<com.example.model.DataModel>>): RepositoryLocal<List<com.example.model.DataModel>> {
    override suspend fun getData(word: String): List<com.example.model.DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun setData(appState: AppState) {
        dataSource.setData(appState)
    }

    override suspend fun getFavourites(): List<com.example.model.DataModel> {
        return dataSource.getFavourites()
    }

    override suspend fun setFavourite(word: String, description: String?) {
        dataSource.setFavourite(word, description)
    }

    override suspend fun removeFavourite(word: String) {
        dataSource.removeFavourite(word)
    }

    override suspend fun isFavourite(word: String): Boolean {
        return dataSource.isFavourite(word)
    }
}