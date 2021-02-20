package com.example.prof.model.di

import android.app.Application
import com.example.prof.TranslatorApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {
    // Этот билдер мы вызовем из класса TranslatorApp, который наследует
    // Application
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    // Наш кастомный Application
    fun inject(englishVocabularyApp: TranslatorApp)
}
