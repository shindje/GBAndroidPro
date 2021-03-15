package com.example.koin

import androidx.room.Room
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.RepositoryLocal
import com.example.repository.RepositoryImplementation
import com.example.repository.RepositoryImplementationLocal
import com.example.repository.local.AppDataBase
import com.example.repository.remote.RetrofitImplementation
import com.example.repository.local.RoomDataBaseImplementation
import org.koin.dsl.module

val application = module {
    single<Repository<List<com.example.model.DataModel>>> {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }

    single { Room.databaseBuilder(get(), AppDataBase::class.java, "AppDB").build() }
    single { get<AppDataBase>().getHistoryDao() }
    single { get<AppDataBase>().getFavouriteDao() }
    single<RepositoryLocal<List<com.example.model.DataModel>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(get(), get())
        )
    }
}