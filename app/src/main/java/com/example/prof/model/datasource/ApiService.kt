package com.example.prof.model.datasource

import com.example.prof.model.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
     fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<DataModel>>

}
