package com.example.prof

import com.example.prof.model.AppState

class Contract {

    interface Interactor<T> {

        suspend fun getData(word: String, fromRemoteSource: Boolean): T
    }


    interface Repository<T> {

        suspend fun getData(word: String): T
    }

    interface DataSource<T> {

        suspend fun getData(word: String): T
    }

    interface DataSourceLocal<T> : DataSource<T> {

        suspend fun saveToDB(appState: AppState)
    }

    interface RepositoryLocal<T> : Repository<T> {

        suspend fun saveToDB(appState: AppState)
    }


}