package com.example.prof.iteractor

import com.example.prof.Contract
import com.example.prof.model.AppState
import com.example.prof.model.DataModel
import com.example.prof.model.di.NAME_LOCAL
import com.example.prof.model.di.NAME_REMOTE
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class InteractorImp @Inject constructor(
    @Named(NAME_REMOTE) val repositoryRemote: Contract.Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val repositoryLocal: Contract.Repository<List<DataModel>>
) : Contract.Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            repositoryRemote.getData(word).map { AppState.Success(it) }
        } else {
            repositoryLocal.getData(word).map { AppState.Success(it) }
        }
    }
}
