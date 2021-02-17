package com.example.prof.model.di

import com.example.prof.Contract
import com.example.prof.iteractor.InteractorImp
import com.example.prof.model.DataModel
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Contract.Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Contract.Repository<List<DataModel>>
    ) = InteractorImp(repositoryRemote, repositoryLocal)
}