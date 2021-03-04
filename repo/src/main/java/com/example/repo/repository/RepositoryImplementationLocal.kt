package com.example.repo.repository

import com.example.core.util.convertDataModelSuccessToEntity
import com.example.core.util.mapHistoryEntityToSearchResult
import com.example.model.AppState
import com.example.model.Contract
import com.example.model.DataModel
import com.example.repo.repository.dao.HistoryDao

class RepositoryImplementationLocal(private val dataSource: Contract.DataSourceLocal<List<DataModel>>) :
    Contract.RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}

// Теперь наш локальный репозиторий работает. Передаём в конструктор
// HistoryDao (вспоминаем в модуле Koin RoomDataBaseImplementation(get())).
class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    Contract.DataSourceLocal<List<DataModel>> {

    // Возвращаем список всех слов в виде понятного для Activity
    // List<SearchResult>
    override suspend fun getData(word: String): List<DataModel> {
        // Метод mapHistoryEntityToSearchResult описан во вспомогательном
        // классе SearchResultParser, в котором есть и другие методы для
        // трансформации данных

        return mapHistoryEntityToSearchResult(
            if (word == "") {
                historyDao.all()
            } else {
                historyDao.getDataByWord(word)
            }
        )
    }

    // Метод сохранения слова в БД. Он будет использоваться в интеракторе
    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }

}
