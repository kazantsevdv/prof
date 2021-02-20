package com.example.prof

import android.app.Activity
import android.app.Application
import com.example.prof.model.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

// Обратите внимание на dispatchingAndroidInjector и интерфейс Dagger’а
// HasActivityInjector: мы переопределяем его метод activityInjector. Они
// нужны для внедрения зависимостей в Activity
class TranslatorApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}
