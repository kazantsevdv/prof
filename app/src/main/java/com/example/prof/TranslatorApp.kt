package com.example.prof

import android.app.Application
import com.example.prof.model.koin.application
import com.example.prof.model.koin.historyScreen
import com.example.prof.model.koin.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class TranslatorApp : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }

    }
}
