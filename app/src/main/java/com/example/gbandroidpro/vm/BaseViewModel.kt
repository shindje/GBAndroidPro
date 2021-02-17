package com.example.gbandroidpro.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gbandroidpro.presenter.SchedulerProvider
import com.example.gbandroidpro.view.AppState
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<T:AppState> (
    protected val liveData: MutableLiveData<T> = MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schdulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

    open fun getData(word: String, isOnline: Boolean): LiveData<T> = liveData

    override fun onCleared() {
        compositeDisposable.clear()
    }
}