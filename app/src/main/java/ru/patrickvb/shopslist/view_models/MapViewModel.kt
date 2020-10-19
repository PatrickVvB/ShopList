package ru.patrickvb.shopslist.view_models

import androidx.lifecycle.MutableLiveData
import ru.patrickvb.shopslist.base.BaseViewModel
import ru.patrickvb.shopslist.models.Shop

class MapViewModel : BaseViewModel() {

    private val shopList = MutableLiveData<ArrayList<Shop>>()
    private val currentShop = MutableLiveData<Shop>()

    fun getShopList(): MutableLiveData<ArrayList<Shop>> {
        return shopList
    }

    fun setShopList(shops: ArrayList<Shop>) {
        shopList.value = shops
    }

    fun getCurrentShop(): MutableLiveData<Shop> {
        return currentShop
    }

    fun setCurrentShop(shop: Shop) {
        currentShop.value = shop
    }
}