package com.example.gbandroidpro.presenter.repo

import com.example.gbandroidpro.DataSource
import com.example.gbandroidpro.DataSourceLocal
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.RepositoryLocal
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.view.AppState

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<DataModel>>): RepositoryLocal<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun setData(appState: AppState) {
        dataSource.setData(appState)
    }
}