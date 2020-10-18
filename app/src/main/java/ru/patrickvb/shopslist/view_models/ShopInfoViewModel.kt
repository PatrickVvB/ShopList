package ru.patrickvb.shopslist.view_models

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.patrickvb.shopslist.base.BaseViewModel
import ru.patrickvb.shopslist.di.App
import ru.patrickvb.shopslist.models.Shop
import ru.patrickvb.shopslist.models.ShopType
import ru.patrickvb.shopslist.repository.AppRepository
import java.net.ConnectException
import java.net.SocketException
import javax.inject.Inject

class ShopInfoViewModel : BaseViewModel() {

    @Inject
    lateinit var repository: AppRepository
    private val shopTypes = MutableLiveData<ArrayList<ShopType>>()
    private val shop = MutableLiveData<Shop>()
    private val job = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + job)

    init {
        App.appComponent.plusAppRepository().injectShopInfoViewModel(this)
    }

    fun getAllShopTypes() {
        vmScope.launch {
            try {
                loadStatus.value = true
                val response = repository.getShopType()

                if (response.code() < 400) {
                    shopTypes.value = response.body()
                } else {
                    showToast("Что то пошло не так :(")
                }
            } catch (e: Exception) {
                when(e) {
                    is ConnectException -> showToast("Нет соединения с сервером")
                    is SocketException -> showToast("Проблемы с соединением")
                }
            }
        }
        loadStatus.value = false
    }

    fun getShop(): MutableLiveData<Shop> {
        return shop
    }

    fun setShop(shop: Shop) {
        this.shop.value = shop
    }

    fun getShopTypes(): MutableLiveData<ArrayList<ShopType>> {
        return shopTypes
    }
}