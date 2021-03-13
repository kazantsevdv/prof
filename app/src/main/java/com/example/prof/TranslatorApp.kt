package com.example.prof

import android.app.Application
import com.example.prof.koin.application
import com.example.prof.koin.historyScreen
import com.example.prof.koin.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class TranslatorApp : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TranslatorApp)
            modules(listOf(application, mainScreen, historyScreen))
        }

    }
}
