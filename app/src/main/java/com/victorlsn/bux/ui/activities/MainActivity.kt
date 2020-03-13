package com.victorlsn.bux.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.victorlsn.bux.R
import com.victorlsn.bux.contracts.ProductsContract
import com.victorlsn.bux.data.api.MyWebSocketListener
import com.victorlsn.bux.data.api.WebSocketMessageHandler
import com.victorlsn.bux.data.api.models.Product
import com.victorlsn.bux.data.api.models.WebSocketMessage
import com.victorlsn.bux.data.api.models.WebSocketResponseMessage
import com.victorlsn.bux.listeners.MessageListener
import com.victorlsn.bux.presenters.ProductsPresenter
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivity :
    BaseActivity(), ProductsContract.View, MessageListener {

    @Inject
    lateinit var messageHandler: WebSocketMessageHandler

    @Inject
    lateinit var presenter: ProductsPresenter

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        messageHandler.setListener(this)
        presenter.requestProductDetails("sb26496")
    }

    override fun onProductDetailsSuccess(product: Product) {

    }

    override fun onProductDetailsFailure(error: String?) {
        val errorMessage = error ?: getString(R.string.default_error_message)
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        loading.show()
    }

    override fun hideLoading() {
        loading.dismiss()
    }

    override fun onMessageReceived(message: WebSocketMessage) {

    }
}