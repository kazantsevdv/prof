package com.example.prof.model.repository

import com.example.prof.Contract
import com.example.prof.model.DataModel
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: Contract.DataSource<List<DataModel>>) :
    Contract.Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}
