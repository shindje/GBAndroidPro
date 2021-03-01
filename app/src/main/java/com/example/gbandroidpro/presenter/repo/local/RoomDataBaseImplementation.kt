package com.example.gbandroidpro.presenter.repo.local

import com.example.gbandroidpro.DataSourceLocal
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.model.Meanings
import com.example.gbandroidpro.model.Translation
import com.example.gbandroidpro.presenter.repo.local.favourite.FavouriteDao
import com.example.gbandroidpro.presenter.repo.local.favourite.FavouriteEntity
import com.example.gbandroidpro.presenter.repo.local.history.HistoryDao
import com.example.gbandroidpro.presenter.repo.local.history.HistoryEntity
import com.example.gbandroidpro.view.AppState

class RoomDataBaseImplementation(private val historyDao: HistoryDao, private val favouriteDao: FavouriteDao) : DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun setData(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let { historyDao.insert(it) }
    }

    override suspend fun getFavourites(): List<DataModel> {
        return mapFavouriteEntityToSearchResult(favouriteDao.all())
    }

    override suspend fun setFavourite(word: String, description: String?) {
        favouriteDao.insert(FavouriteEntity(word, description))
    }

    override suspend fun removeFavourite(word: String) {
        favouriteDao.delete(FavouriteEntity(word, null))
    }

    override suspend fun isFavourite(word: String): Boolean {
        var favouriteEntity = favouriteDao.findByWord(word)
        return favouriteEntity != null
    }


}

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataModel> {
    val dataModelList = ArrayList<DataModel>()
    for (entity in list) {
        dataModelList.add(DataModel(entity.word, null))
    }
    return dataModelList
}

fun mapFavouriteEntityToSearchResult(list: List<FavouriteEntity>): List<DataModel> {
    val dataModelList = ArrayList<DataModel>()
    for (entity in list) {
        dataModelList.add(DataModel(entity.word, listOf(Meanings(Translation(entity.description), null)), true))
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