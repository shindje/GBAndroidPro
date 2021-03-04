package com.example.gbandroidpro.vm

import androidx.lifecycle.LiveData
import com.example.gbandroidpro.model.parseSearchResults
import com.example.gbandroidpro.presenter.MainInteractor
import com.example.gbandroidpro.view.AppState
import kotlinx.coroutines.launch

class MainViewModel constructor(private val interactor: MainInteractor)
    : BaseViewModel<AppState>() {

    fun subscribe(): LiveData<AppState> {
        return liveData
    }

    override fun getData(word: String, isOnline: Boolean) {
        liveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            liveData.postValue(parseSearchResults(interactor.getData(word, isOnline)))
        }
    }

    fun setFavourite(word: String, description: String?) {
        viewModelCoroutineScope.launch {
            interactor.setFavourite(word, description)
        }
    }

    fun removeFavourite(word: String) {
        viewModelCoroutineScope.launch {
            interactor.removeFavourite(word)
        }
    }

    fun getFavourites() {
        liveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            liveData.postValue(parseSearchResults(interactor.getFavourites()))
        }
    }

    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        liveData.value = AppState.Success(null)
        super.onCleared()
    }
}