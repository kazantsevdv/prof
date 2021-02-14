package com.example.prof.iteractor

import com.example.prof.Contract
import com.example.prof.model.AppState
import com.example.prof.model.DataModel
import io.reactivex.Observable

class InteractorImp(
    private val remoteRepository: Contract.Repository<List<DataModel>>,
    private val localRepository: Contract.Repository<List<DataModel>>
) : Contract.Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}
