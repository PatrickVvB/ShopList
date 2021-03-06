package ru.patrickvb.shopslist.view_models

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.patrickvb.shopslist.base.BaseViewModel
import ru.patrickvb.shopslist.di.App
import ru.patrickvb.shopslist.models.Promotion
import ru.patrickvb.shopslist.repository.AppRepository
import ru.patrickvb.shopslist.repository.ImageRepository
import java.net.ConnectException
import java.net.SocketException
import javax.inject.Inject

class PromotionViewModel : BaseViewModel() {

    @Inject
    lateinit var repository: AppRepository
    @Inject
    lateinit var imageRepository: ImageRepository
    private val job = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + job)
    private val promotionsList = MutableLiveData<ArrayList<Promotion>>()
    private val promotionImage = MutableLiveData<String>()
    private val shopId = MutableLiveData<Int>()

    init {
        App.appComponent.plusAppRepository().injectPromotionViewModel(this)
    }

    fun getPromotions() {
        vmScope.launch {
            try {
                loadStatus.value = true

                shopId.value?.let {
                    val response = repository.getDiscountsById(it)

                    if (response.code() < 400) {
                        promotionsList.value = response.body()
                    } else {
                        showToast("Что то пошло не так :(")
                    }
                } ?: showToast("Магазин не найден")
            } catch (e: Exception) {
                when(e) {
                    is ConnectException -> showToast("Нет соединения с сервером")
                    is SocketException -> showToast("Проблемы с соединением")
                    else -> showToast(e.toString())
                }
            }
        }
        loadStatus.value = false
    }

    fun getPromotionImage(url: String) {
        vmScope.launch {
            try {
                loadStatus.value = true
                val response = imageRepository.getDiscountImage(url)

                if (response.code() < 400) {
                    promotionImage.value = response.body()
                } else {
                    showToast("Что то пошло не так :(")
                }
            } catch (e: Exception) {
                when(e) {
                    is ConnectException -> showToast("Нет соединения с сервером")
                    is SocketException -> showToast("Проблемы с соединением")
                    else -> showToast(e.toString())
                }
            }
        }
        loadStatus.value = false
    }

    fun getPromotionsList(): MutableLiveData<ArrayList<Promotion>> {
        return promotionsList
    }

    fun getPromotionImage(): MutableLiveData<String> {
        return promotionImage
    }

    fun setShopId(id: Int) {
        return shopId.setValue(id)
    }
}