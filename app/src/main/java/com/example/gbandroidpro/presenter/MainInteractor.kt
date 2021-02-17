package com.example.gbandroidpro.presenter

import com.example.gbandroidpro.Interactor
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.di.NAME_LOCAL
import com.example.gbandroidpro.di.NAME_REMOTE
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.view.AppState
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {
    // Интерактор лишь запрашивает у репозитория данные, детали имплементации
    // интерактору неизвестны
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}