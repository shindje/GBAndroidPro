package com.example.gbandroidpro.presenter.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gbandroidpro.presenter.repo.local.favourite.FavouriteDao
import com.example.gbandroidpro.presenter.repo.local.favourite.FavouriteEntity
import com.example.gbandroidpro.presenter.repo.local.history.HistoryDao
import com.example.gbandroidpro.presenter.repo.local.history.HistoryEntity

@Database(entities = arrayOf(HistoryEntity::class, FavouriteEntity::class), version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
    abstract fun getFavouriteDao(): FavouriteDao
}