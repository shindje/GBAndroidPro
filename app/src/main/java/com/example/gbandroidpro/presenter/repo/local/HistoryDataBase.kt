package com.example.gbandroidpro.presenter.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryEntity::class), version = 1, exportSchema = false)
abstract class HistoryDataBase: RoomDatabase() {
    abstract fun getDao(): HistoryDao
}