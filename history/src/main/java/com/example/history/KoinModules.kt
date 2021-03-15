package com.example.history

import com.example.history.presenter.HistoryInteractor
import com.example.history.vm.HistoryViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDependencies() = loadModules
private val loadModules by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}