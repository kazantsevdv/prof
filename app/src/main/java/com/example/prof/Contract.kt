package com.example.prof

import com.example.prof.model.AppState
import io.reactivex.Observable

class Contract {
    interface Presenter<T : AppState, V : View> {

        fun attachView(view: V)

        fun detachView(view: V)

        fun getData(word: String, isOnline: Boolean)
    }

    interface Interactor<T> {

        fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
    }

    interface View {

        fun renderData(appState: AppState)

    }

    interface Repository<T> {

        fun getData(word: String): Observable<T>
    }

    interface DataSource<T> {

        fun getData(word: String): Observable<T>
    }

}