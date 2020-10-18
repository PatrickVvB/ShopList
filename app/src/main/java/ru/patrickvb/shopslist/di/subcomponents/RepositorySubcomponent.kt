package ru.patrickvb.shopslist.di.subcomponents

import dagger.Subcomponent
import ru.patrickvb.shopslist.di.moduls.RepositoryModule
import ru.patrickvb.shopslist.di.scopes.ViewModelScope
import ru.patrickvb.shopslist.view_models.MainViewModel
import ru.patrickvb.shopslist.view_models.ShopInfoViewModel

@ViewModelScope
@Subcomponent(modules = [RepositoryModule::class])
interface RepositorySubcomponent {

    fun injectMainViewModel(vm: MainViewModel)
    fun injectShopInfoViewModel(vm: ShopInfoViewModel)
}