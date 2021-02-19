package com.example.gbandroidpro.vm

import androidx.lifecycle.LiveData
import com.example.gbandroidpro.Interactor
import com.example.gbandroidpro.model.parseSearchResults
import com.example.gbandroidpro.presenter.MainInteractor
import com.example.gbandroidpro.presenter.repo.DataSourceLocal
import com.example.gbandroidpro.presenter.repo.DataSourceRemote
import com.example.gbandroidpro.presenter.repo.RepositoryImplementation
import com.example.gbandroidpro.view.AppState
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        liveData.value = AppState.Success(null)
        super.onCleared()
    }
}