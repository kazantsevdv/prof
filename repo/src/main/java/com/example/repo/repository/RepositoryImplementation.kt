package com.example.repo.repository

import com.example.model.Contract
import com.example.model.DataModel

class RepositoryImplementation(private val dataSource: Contract.DataSource<List<DataModel>>) :
    Contract.Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
