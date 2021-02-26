package com.example.gbandroidpro.presenter.repo.local

import com.example.gbandroidpro.DataSource
import com.example.gbandroidpro.DataSourceLocal
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.view.AppState
import io.reactivex.Observable

class RoomDataBaseImplementation(private val historyDao: HistoryDao) : DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun setData(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let { historyDao.insert(it) }
    }
}

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataModel> {
    val dataModelList = ArrayList<DataModel>()
    for (entity in list) {
        dataModelList.add(DataModel(entity.word, null))
    }
    return dataModelList
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val data = appState.data
            if (data.isNullOrEmpty() || data[0].text.isNullOrEmpty())
                null
            else
                HistoryEntity(data[0].text!!, null)
        }
        else -> null
    }
}