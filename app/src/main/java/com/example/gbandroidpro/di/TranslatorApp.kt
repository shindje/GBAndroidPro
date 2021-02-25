package com.example.gbandroidpro.di

import android.app.Activity
import android.app.Application
import com.example.gbandroidpro.di.dagger.DaggerAppComponent
import com.example.gbandroidpro.di.koin.application
import com.example.gbandroidpro.di.koin.mainScreen
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import org.koin.core.context.startKoin
import javax.inject.Inject

class TranslatorApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(listOf(application, mainScreen)) }
    }
}