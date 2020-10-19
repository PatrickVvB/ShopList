package ru.patrickvb.shopslist.view_models

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.patrickvb.shopslist.base.BaseViewModel
import ru.patrickvb.shopslist.di.App
import ru.patrickvb.shopslist.models.Promotion
import ru.patrickvb.shopslist.repository.AppRepository
import java.net.ConnectException
import java.net.SocketException
import javax.inject.Inject

class PromotionViewModel : BaseViewModel() {

    @Inject
    lateinit var repository: AppRepository
    private val job = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + job)
    private val promotionsList = MutableLiveData<ArrayList<Promotion>>()
    private val promotionImage = MutableLiveData<Bitmap>()

    init {
        App.appComponent.plusAppRepository().injectPromotionViewModel(this)
    }

    fun getPromotions(shopId: Int) {
        vmScope.launch {
            try {
                loadStatus.value = true
                val response = repository.getDiscountsById(shopId)

                if (response.code() < 400) {
                    promotionsList.value = response.body()
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

    fun getPromotionImage(url: String) {
        vmScope.launch {
            try {
                loadStatus.value = true
                val response = repository.getDiscountImage(url)

                if (response.code() < 400) {
                    promotionImage.value = response.body()
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

    fun getPromotionsList(): MutableLiveData<ArrayList<Promotion>> {
        return promotionsList
    }

    fun getPromotionImage(): MutableLiveData<Bitmap> {
        return promotionImage
    }
}