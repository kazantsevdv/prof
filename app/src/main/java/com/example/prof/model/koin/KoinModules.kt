package com.example.prof.model.koin

import com.example.prof.Contract
import com.example.prof.iteractor.InteractorImp
import com.example.prof.model.DataModel
import com.example.prof.model.datasource.RetrofitImplementation
import com.example.prof.model.repository.RepositoryImplementation
import com.example.prof.viewmodel.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val application = module {
    single<Contract.Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }
    single<Contract.Repository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }
}

val mainScreen = module {
    factory { InteractorImp(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}

