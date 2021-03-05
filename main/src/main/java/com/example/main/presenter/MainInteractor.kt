package com.example.main.presenter

import com.example.core.presenter.Interactor
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.RepositoryLocal
import com.example.model.AppState

class MainInteractor constructor(
    val remoteRepository: Repository<List<com.example.model.DataModel>>,
    val localRepository: RepositoryLocal<List<com.example.model.DataModel>>
) : Interactor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getData(word))
            localRepository.setData(appState)
        } else {
            appState = AppState.Success(localRepository.getData(word))
        }
        //check Favourites
        if (appState is AppState.Success && appState.data != null)
            for (dataModel in appState.data!!)
                dataModel.isFavorite = localRepository.isFavourite(dataModel.text!!)
        return appState
    }

    suspend fun setFavourite(word: String, description: String?) {
        localRepository.setFavourite(word, description)
    }

    suspend fun removeFavourite(word: String) {
        localRepository.removeFavourite(word)
    }

    suspend fun getFavourites(): AppState {
        return AppState.Success(localRepository.getFavourites())
    }
}