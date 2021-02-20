package com.example.prof.viewmodel

import androidx.lifecycle.LiveData
import com.example.prof.iteractor.InteractorImp
import com.example.prof.model.AppState
import com.example.prof.model.datasource.DataSourceLocal
import com.example.prof.model.datasource.DataSourceRemote
import com.example.prof.model.repository.RepositoryImplementation
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject


class MainViewModel @Inject constructor(private val interactor: InteractorImp) :
    BaseViewModel<AppState>()

{
    // В этой переменной хранится последнее состояние Activity
    private var appState: AppState? = null


    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }
    // Переопределяем метод из BaseViewModel
    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }

                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            // Данные успешно загружены; сохраняем их и передаем во View (через
            // LiveData). View сама разберётся, как их отображать
            override fun onNext(state: AppState) {
                appState = state
                liveDataForViewToObserve.value = state
            }

            // В случае ошибки передаём её в Activity таким же образом через LiveData
            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}
