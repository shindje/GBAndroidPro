package com.example.gbandroidpro.di.koin

import androidx.room.Room
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.RepositoryLocal
import com.example.history.presenter.HistoryInteractor
import com.example.history.vm.HistoryViewModel
import com.example.main.presenter.MainInteractor
import com.example.main.vm.MainViewModel
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

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}