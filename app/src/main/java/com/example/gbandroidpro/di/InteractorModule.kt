package com.example.gbandroidpro.di

import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.presenter.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {
    @Provides
    internal fun provideIteractor(
        @Named(NAME_REMOTE)repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL)repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}