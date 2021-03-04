package com.example.prof.iteractor

import com.example.model.Contract
import com.example.model.AppState
import com.example.model.DataModel

class HistoryInteractor(
    private val repositoryRemote: Contract.Repository<List<DataModel>>,
    private val repositoryLocal: Contract.RepositoryLocal<List<DataModel>>
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
