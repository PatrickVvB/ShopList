package ru.patrickvb.shopslist.di

import android.app.Application
import ru.patrickvb.shopslist.di.components.AppComponent
import ru.patrickvb.shopslist.di.components.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        injectDagger()
    }

    private fun injectDagger() {
        appComponent = DaggerAppComponent.create()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}