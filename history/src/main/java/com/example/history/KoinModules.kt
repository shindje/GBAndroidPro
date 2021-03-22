package com.example.history

import com.example.history.presenter.HistoryInteractor
import com.example.history.view.HistoryActivity
import com.example.history.vm.HistoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadModules
private val loadModules by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    scope(named<HistoryActivity>()) {
        viewModel { HistoryViewModel(get()) }
        scoped { HistoryInteractor(get(), get()) }
    }
}