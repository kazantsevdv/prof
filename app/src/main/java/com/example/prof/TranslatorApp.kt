package com.example.prof

import android.app.Application
import com.example.prof.model.koin.application
import com.example.prof.model.koin.mainScreen
import org.koin.core.context.startKoin


// Обратите внимание на dispatchingAndroidInjector и интерфейс Dagger’а
// HasActivityInjector: мы переопределяем его метод activityInjector. Они
// нужны для внедрения зависимостей в Activity
class TranslatorApp : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }

    }
}
