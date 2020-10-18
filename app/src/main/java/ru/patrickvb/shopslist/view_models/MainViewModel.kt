package ru.patrickvb.shopslist.view_models

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.patrickvb.shopslist.base.BaseViewModel
import ru.patrickvb.shopslist.di.App
import ru.patrickvb.shopslist.models.Shop
import ru.patrickvb.shopslist.repository.AppRepository
import java.net.ConnectException
import java.net.SocketException
import javax.inject.Inject

class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var repository: AppRepository
    private val job = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + job)
    private val shopList = MutableLiveData<ArrayList<Shop>>()

    init {
        App.appComponent.plusAppRepository().injectMainViewModel(this)
    }

    fun getShops() {
        vmScope.launch {
            try {
                loadStatus.value = true
                val response = repository.getShops()

                if (response.code() < 400) {
                    shopList.value = response.body()
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

    fun getShopList(): MutableLiveData<ArrayList<Shop>> {
        return shopList
    }
}