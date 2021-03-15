package com.example.main

import com.example.main.presenter.MainInteractor
import com.example.main.view.MainActivity
import com.example.main.vm.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadModules
private val loadModules by lazy {
    loadKoinModules(listOf(mainScreen))
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
        scoped { MainInteractor(get(), get()) }
    }
}