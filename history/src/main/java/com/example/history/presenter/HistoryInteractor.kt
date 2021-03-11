package com.example.history.presenter

import com.example.core.presenter.Interactor
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.RepositoryLocal
import com.example.model.AppState

class HistoryInteractor(
    private val repositoryRemote: Repository<List<com.example.model.DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<com.example.model.DataModel>>
): Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success (
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}