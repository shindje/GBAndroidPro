package com.example.gbandroidpro.di

import android.app.Application
import com.example.koin.application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class TranslatorApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TranslatorApp)
            loadKoinModules(listOf(application))
        }
    }
}