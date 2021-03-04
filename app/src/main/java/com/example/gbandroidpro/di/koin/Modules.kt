package com.example.gbandroidpro.di.koin

import androidx.room.Room
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.RepositoryLocal
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.presenter.HistoryInteractor
import com.example.gbandroidpro.presenter.MainInteractor
import com.example.gbandroidpro.presenter.repo.RepositoryImplementation
import com.example.gbandroidpro.presenter.repo.RepositoryImplementationLocal
import com.example.gbandroidpro.presenter.repo.local.AppDataBase
import com.example.gbandroidpro.presenter.repo.remote.RetrofitImplementation
import com.example.gbandroidpro.presenter.repo.local.RoomDataBaseImplementation
import com.example.gbandroidpro.vm.HistoryViewModel
import com.example.gbandroidpro.vm.MainViewModel
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }

    single { Room.databaseBuilder(get(), AppDataBase::class.java, "AppDB").build() }
    single { get<AppDataBase>().getHistoryDao() }
    single { get<AppDataBase>().getFavouriteDao() }
    single<RepositoryLocal<List<DataModel>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get(), get())) }
}

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}