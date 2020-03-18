package com.victorlsn.bux.ui.activities

import android.os.Bundle
import com.victorlsn.bux.R
import com.victorlsn.bux.data.api.websocket.MyWebSocketListener
import com.victorlsn.bux.data.api.websocket.SocketWrapper
import com.victorlsn.bux.ui.fragments.ProductsFragment
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

class MainActivity :
    BaseActivity() {
    private val fragmentManager = supportFragmentManager

    @Inject
    lateinit var socketWrapper: SocketWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        socketWrapper.connectSocket()
        setupProductFragment()
    }

    private fun setupProductFragment() {
        val productsFragment = ProductsFragment.newInstance()
        fragmentManager.beginTransaction()
            .add(R.id.main_container, productsFragment, "Products").commit()
    }
}