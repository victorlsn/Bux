package com.victorlsn.bux.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import com.victorlsn.bux.R
import com.victorlsn.bux.ui.fragments.BaseFragment
import com.victorlsn.bux.util.extensions.getLoading
import dagger.android.support.DaggerAppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : DaggerAppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    var activeFragment: BaseFragment? = null
    open lateinit var loading: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        loading = getLoading()
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        var handled = false
        activeFragment?.let {
            if (activeFragment!!.isAdded) {
                handled = it.onBackPressed()
            }
        }
        if (!handled) {
            if (!doubleBackToExitPressedOnce) {
                doubleBackToExitPressedOnce = true
                Toast.makeText(this, getString(R.string.press_back_again), Toast.LENGTH_SHORT).show()
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            } else {
                super.onBackPressed()
            }
        }
    }
}