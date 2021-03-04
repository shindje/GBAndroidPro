package com.example.gbandroidpro.presenter.repo.local.history

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("select * from HistoryEntity")
    suspend fun all(): List<HistoryEntity>

    @Query("select * from HistoryEntity where word like :word")
    suspend fun findByWord(word: String): HistoryEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<HistoryEntity>)

    @Update
    suspend fun update(entity: HistoryEntity)

    @Delete
    suspend fun delete(entity: HistoryEntity)
}