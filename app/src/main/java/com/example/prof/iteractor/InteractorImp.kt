package com.example.prof.iteractor

import com.example.prof.Contract
import com.example.prof.model.AppState
import com.example.prof.model.DataModel

class InteractorImp(
    private val repositoryRemote: Contract.Repository<List<DataModel>>,
    private val repositoryLocal: Contract.Repository<List<DataModel>>
) : Contract.Interactor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }

}

