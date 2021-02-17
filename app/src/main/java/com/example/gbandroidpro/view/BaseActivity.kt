package com.example.gbandroidpro.view

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.gbandroidpro.Interactor

abstract class BaseActivity<T : AppState, V: Interactor<T>> : AppCompatActivity() {
    abstract val model: ViewModel
    abstract fun renderData(appState: AppState)
}