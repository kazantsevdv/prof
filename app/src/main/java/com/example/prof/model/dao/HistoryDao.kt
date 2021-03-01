package com.example.prof.model.dao

import androidx.room.*

@Dao
interface HistoryDao {
    // Получить весь список слов
    @Query("SELECT * FROM HistoryEntity")
    suspend fun all(): List<HistoryEntity>
    // Получить конкретное слово
    @Query("SELECT * FROM HistoryEntity WHERE word LIKE :word")
    suspend fun getDataByWord(word: String): List<HistoryEntity>
    // Сохранить новое слово
    // onConflict = OnConflictStrategy.IGNORE означает, что дубликаты не будут
    // сохраняться
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: HistoryEntity)
    // Вставить список слов
    // onConflict = OnConflictStrategy.IGNORE означает, что дубликаты не будут
    // сохраняться
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<HistoryEntity>)
    // Обновить слово
    @Update
    suspend fun update(entity: HistoryEntity)
    // Удалить слово
    @Delete
    suspend fun delete(entity: HistoryEntity)
}
