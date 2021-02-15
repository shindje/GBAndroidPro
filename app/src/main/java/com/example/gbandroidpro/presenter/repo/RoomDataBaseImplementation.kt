package com.example.gbandroidpro.presenter.repo

import com.example.gbandroidpro.DataSource
import com.example.gbandroidpro.model.DataModel
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented") // To change body of created functions use File
        // | Settings | File Templates.
    }
}