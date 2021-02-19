package com.example.gbandroidpro.presenter.repo

import com.example.gbandroidpro.DataSource
import com.example.gbandroidpro.model.DataModel
import io.reactivex.Observable

// Для получения внешних данных мы будем использовать Retrofit
class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<DataModel>> {

    override suspend fun  getData(word: String): List<DataModel> = remoteProvider.getData(word)
}
// Для локальных данных используется Room
class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override suspend fun  getData(word: String): List<DataModel> = remoteProvider.getData(word)
}