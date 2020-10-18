package ru.patrickvb.shopslist.di.moduls

import dagger.Binds
import dagger.Module
import ru.patrickvb.shopslist.di.scopes.ViewModelScope
import ru.patrickvb.shopslist.repository.AppRepository
import ru.patrickvb.shopslist.repository.AppRepositoryImpl

@Module
interface RepositoryModule {

    @ViewModelScope
    @Binds
    fun provideAppRepository(repository: AppRepositoryImpl): AppRepository
}