package com.example.gbandroidpro.di

import com.example.gbandroidpro.DataSource
import com.example.gbandroidpro.Repository
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.presenter.repo.DataSourceRemote
import com.example.gbandroidpro.presenter.repo.RepositoryImplementation
import com.example.gbandroidpro.presenter.repo.RetrofitImplementation
import com.example.gbandroidpro.presenter.repo.RoomDataBaseImplementation
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE)dataSourceRemote: DataSource<List<DataModel>>)
        : Repository<List<DataModel>> = RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL)dataSourceLocal: DataSource<List<DataModel>>)
            : Repository<List<DataModel>> = RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> = RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> = RoomDataBaseImplementation()
}