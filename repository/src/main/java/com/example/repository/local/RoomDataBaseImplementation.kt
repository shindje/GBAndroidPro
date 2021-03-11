package com.example.repository.local

import com.example.gbandroidpro.DataSourceLocal
import com.example.repository.local.favourite.FavouriteDao
import com.example.repository.local.favourite.FavouriteEntity
import com.example.repository.local.history.HistoryDao
import com.example.repository.local.history.HistoryEntity
import com.example.model.AppState

class RoomDataBaseImplementation(private val historyDao: HistoryDao, private val favouriteDao: FavouriteDao) : DataSourceLocal<List<com.example.model.DataModel>> {

    override suspend fun getData(word: String): List<com.example.model.DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun setData(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let { historyDao.insert(it) }
    }

    override suspend fun getFavourites(): List<com.example.model.DataModel> {
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

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<com.example.model.DataModel> {
    val dataModelList = ArrayList<com.example.model.DataModel>()
    for (entity in list) {
        dataModelList.add(com.example.model.DataModel(entity.word, null))
    }
    return dataModelList
}

fun mapFavouriteEntityToSearchResult(list: List<FavouriteEntity>): List<com.example.model.DataModel> {
    val dataModelList = ArrayList<com.example.model.DataModel>()
    for (entity in list) {
        dataModelList.add(
            com.example.model.DataModel(
                entity.word,
                listOf(
                    com.example.model.Meanings(
                        com.example.model.Translation(entity.description),
                        null
                    )
                ),
                true
            )
        )
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