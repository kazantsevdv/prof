package com.example.prof.koin


import androidx.room.Room
import com.example.model.Contract
import com.example.model.DataModel
import com.example.prof.iteractor.HistoryInteractor
import com.example.prof.iteractor.MainInteractor
import com.example.prof.view.HistoryActivity
import com.example.prof.view.MainActivity
import com.example.prof.viewmodel.HistoryViewModel
import com.example.prof.viewmodel.MainViewModel
import com.example.repo.repository.RepositoryImplementation
import com.example.repo.repository.RepositoryImplementationLocal
import com.example.repo.repository.RoomDataBaseImplementation
import com.example.repo.repository.dao.HistoryDataBase
import com.example.repo.repository.datasource.RetrofitImplementation
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

//fun injectDependencies() = loadModules
//
//private val loadModules by lazy {
//    loadKoinModules(listOf(application, mainScreen,historyScreen))
//}

val application = module {

    single<Contract.Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<Contract.RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(get())
        )
    }


    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB")
        .fallbackToDestructiveMigration()
        .build() }
    single { get<com.example.repo.repository.dao.HistoryDataBase>().historyDao() }


}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}

//val mainScreen = module {
//    factory { MainViewModel(get()) }
//    factory { MainInteractor(get(), get()) }
//
//}


val historyScreen = module {
    scope(named<HistoryActivity>()) {
        scoped { HistoryInteractor(get(), get()) }
    }
    viewModel { HistoryViewModel(get()) }

}

