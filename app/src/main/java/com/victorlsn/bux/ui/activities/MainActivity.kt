package com.victorlsn.bux.ui.activities

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.victorlsn.bux.R
import com.victorlsn.bux.ui.fragments.ProductsFragment

class MainActivity :
    BaseActivity() {
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupProductFragment()
    }

    private fun setupProductFragment() {
        val productsFragment = ProductsFragment.newInstance()
        fragmentManager.beginTransaction()
            .add(R.id.main_container, productsFragment, "Products").commit()
    }


}