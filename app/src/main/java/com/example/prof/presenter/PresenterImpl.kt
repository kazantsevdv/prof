package com.example.prof.presenter

import com.example.prof.Contract
import com.example.prof.iteractor.InteractorImp
import com.example.prof.model.AppState
import com.example.prof.model.datasource.DataSourceLocal
import com.example.prof.model.datasource.DataSourceRemote
import com.example.prof.model.repository.RepositoryImplementation
import com.example.prof.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class PresenterImpl<T : AppState, V : Contract.View>(
    private val interactor: InteractorImp = InteractorImp(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Contract.Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }
        }
    }
}
