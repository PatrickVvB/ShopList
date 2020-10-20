package ru.patrickvb.shopslist.ui.activities

import android.os.Bundle
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.base.BaseActivity
import ru.patrickvb.shopslist.ui.fragments.MainFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            replaceFragment(MainFragment())
    }
}