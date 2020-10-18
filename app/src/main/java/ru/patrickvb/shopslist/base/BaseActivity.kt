package ru.patrickvb.shopslist.base

import androidx.appcompat.app.AppCompatActivity
import ru.patrickvb.shopslist.R

abstract class BaseActivity : AppCompatActivity() {

    fun addFragment(fragment: BaseFragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("")
            .replace(R.id.container, fragment)
            .commit()
    }

    fun replaceFragment(fragment: BaseFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}