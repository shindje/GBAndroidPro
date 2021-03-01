package com.example.gbandroidpro.presenter

import com.example.gbandroidpro.Interactor
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.RepositoryLocal
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.view.AppState

class MainInteractor constructor(
    val remoteRepository: Repository<List<DataModel>>,
    val localRepository: RepositoryLocal<List<DataModel>>
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
            for (dataModel in appState.data)
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