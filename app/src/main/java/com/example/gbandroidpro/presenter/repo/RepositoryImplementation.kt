package com.example.gbandroidpro.presenter.repo

import com.example.gbandroidpro.DataSource
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.model.DataModel
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {
    // Репозиторий возвращает данные, используя dataSource (локальный или
    // внешний)
    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}