package com.example.gbandroidpro.presenter

import com.example.gbandroidpro.Interactor
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.di.dagger.NAME_LOCAL
import com.example.gbandroidpro.di.dagger.NAME_REMOTE
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.view.AppState
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor constructor(
    val remoteRepository: Repository<List<DataModel>>,
    val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                remoteRepository
            } else {
                localRepository
            }.getData(word)
        )
    }
}