package ru.patrickvb.shopslist.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val toastObserver = MutableLiveData<String>()
    val loadStatus = MutableLiveData<Boolean>()

    fun showToast(message: String) {
        toastObserver.value = message
    }

    fun getToast(): MutableLiveData<String> {
        return toastObserver
    }
}