package com.example.gbandroidpro.di

import android.app.Application
import com.example.gbandroidpro.di.koin.application
import com.example.gbandroidpro.di.koin.historyScreen
import com.example.gbandroidpro.di.koin.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TranslatorApp)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}