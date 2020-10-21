package ru.patrickvb.shopslist.di.components

import dagger.Component
import ru.patrickvb.shopslist.di.moduls.NetworkImageModule
import ru.patrickvb.shopslist.di.moduls.NetworkModule
import ru.patrickvb.shopslist.di.subcomponents.RepositorySubcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, NetworkImageModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun plusAppRepository(): RepositorySubcomponent
}