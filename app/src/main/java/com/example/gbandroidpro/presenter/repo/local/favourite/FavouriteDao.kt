package com.example.gbandroidpro.presenter.repo.local.favourite

import androidx.room.*

@Dao
interface FavouriteDao {
    @Query("select * from FavouriteEntity")
    suspend fun all(): List<FavouriteEntity>

    @Query("select * from FavouriteEntity where word like :word")
    suspend fun findByWord(word: String): FavouriteEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FavouriteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<FavouriteEntity>)

    @Update
    suspend fun update(entity: FavouriteEntity)

    @Delete
    suspend fun delete(entity: FavouriteEntity)
}