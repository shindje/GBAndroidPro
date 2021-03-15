package com.example.main

import com.example.main.presenter.MainInteractor
import com.example.main.vm.MainViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDependencies() = loadModules
private val loadModules by lazy {
    loadKoinModules(listOf(mainScreen))
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}