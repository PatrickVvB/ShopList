package ru.patrickvb.shopslist.base

import androidx.fragment.app.Fragment
import ru.patrickvb.shopslist.R

abstract class BaseFragment : Fragment() {

    fun addFragment(fragment: BaseFragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .addToBackStack("")
            .replace(R.id.container, fragment)
            .commit()
    }

    fun replaceFragment(fragment: BaseFragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}