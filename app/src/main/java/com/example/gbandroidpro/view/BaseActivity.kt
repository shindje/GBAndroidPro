package com.example.gbandroidpro.view

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.gbandroidpro.Interactor
import com.example.gbandroidpro.vm.BaseViewModel

abstract class BaseActivity<T : AppState, V: Interactor<T>> : AppCompatActivity() {
    abstract val model: BaseViewModel<T>
    abstract fun renderData(appState: AppState)
}