package com.example.prof.model.koin

import androidx.room.Room
import com.example.prof.Contract
import com.example.prof.iteractor.HistoryInteractor
import com.example.prof.iteractor.MainInteractor
import com.example.prof.model.DataModel
import com.example.prof.model.dao.HistoryDataBase
import com.example.prof.model.datasource.RetrofitImplementation
import com.example.prof.model.repository.RepositoryImplementation
import com.example.prof.model.repository.RepositoryImplementationLocal
import com.example.prof.model.repository.RoomDataBaseImplementation
import com.example.prof.viewmodel.HistoryViewModel
import com.example.prof.viewmodel.MainViewModel
import org.koin.dsl.module


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
    single { get<HistoryDataBase>().historyDao() }


}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }

}
val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}

