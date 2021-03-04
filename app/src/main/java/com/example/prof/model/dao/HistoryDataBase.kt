package com.example.prof.model.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryEntity::class), version = 3, exportSchema = false)
abstract class HistoryDataBase : RoomDatabase() {
    // Возвращаем DAO
    abstract fun historyDao(): HistoryDao
}