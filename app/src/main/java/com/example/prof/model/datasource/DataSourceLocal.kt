package com.example.prof.model.datasource

import com.example.prof.Contract
import com.example.prof.model.DataModel
import io.reactivex.Observable

class DataSourceLocal(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    Contract.DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
