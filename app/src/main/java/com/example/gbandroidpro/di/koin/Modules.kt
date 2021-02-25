package com.example.gbandroidpro.di.koin

import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.di.dagger.NAME_LOCAL
import com.example.gbandroidpro.di.dagger.NAME_REMOTE
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.presenter.MainInteractor
import com.example.gbandroidpro.presenter.repo.RepositoryImplementation
import com.example.gbandroidpro.presenter.repo.RetrofitImplementation
import com.example.gbandroidpro.presenter.repo.RoomDataBaseImplementation
import com.example.gbandroidpro.vm.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImplementation(RetrofitImplementation()) }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImplementation(RoomDataBaseImplementation()) }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}