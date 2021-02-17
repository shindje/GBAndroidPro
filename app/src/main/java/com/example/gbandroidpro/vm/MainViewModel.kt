package com.example.gbandroidpro.vm

import androidx.lifecycle.LiveData
import com.example.gbandroidpro.Interactor
import com.example.gbandroidpro.presenter.MainInteractor
import com.example.gbandroidpro.presenter.repo.DataSourceLocal
import com.example.gbandroidpro.presenter.repo.DataSourceRemote
import com.example.gbandroidpro.presenter.repo.RepositoryImplementation
import com.example.gbandroidpro.view.AppState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor)
    : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
                interactor.getData(word, isOnline)
                        .subscribeOn(schdulerProvider.io())
                        .observeOn(schdulerProvider.ui())
                        .doOnSubscribe { liveData.value = AppState.Loading(null) }
                        .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun getObserver(): DisposableObserver<AppState> = object:
            DisposableObserver<AppState>() {
                override fun onNext(t: AppState) {
                    appState = t
                    liveData.value = t
                }

                override fun onError(e: Throwable) {
                    liveData.value = AppState.Error(e)
                }

                override fun onComplete() {
                }
            }
}