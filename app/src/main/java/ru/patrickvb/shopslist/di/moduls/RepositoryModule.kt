package ru.patrickvb.shopslist.di.moduls

import dagger.Binds
import dagger.Module
import ru.patrickvb.shopslist.di.scopes.ViewModelScope
import ru.patrickvb.shopslist.repository.AppRepository
import ru.patrickvb.shopslist.repository.impls.AppRepositoryImpl
import ru.patrickvb.shopslist.repository.ImageRepository
import ru.patrickvb.shopslist.repository.impls.ImageRepositoryImpl

@Module
interface RepositoryModule {

    @ViewModelScope
    @Binds
    fun provideAppRepository(repository: AppRepositoryImpl): AppRepository

    @ViewModelScope
    @Binds
    fun provideImageRepository(repository: ImageRepositoryImpl): ImageRepository
}