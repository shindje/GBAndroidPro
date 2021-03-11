package com.example.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repository.local.favourite.FavouriteDao
import com.example.repository.local.favourite.FavouriteEntity
import com.example.repository.local.history.HistoryDao
import com.example.repository.local.history.HistoryEntity

@Database(entities = arrayOf(HistoryEntity::class, FavouriteEntity::class), version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
    abstract fun getFavouriteDao(): FavouriteDao
}