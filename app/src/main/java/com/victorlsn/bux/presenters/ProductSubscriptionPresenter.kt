package com.victorlsn.bux.presenters

import android.os.Handler
import com.victorlsn.bux.contracts.ProductSubscriptionContract
import com.victorlsn.bux.data.api.models.WebSocketMessage
import com.victorlsn.bux.data.api.websocket.SocketWrapper
import com.victorlsn.bux.listeners.ProductSubscriptionListener
import javax.inject.Inject

class ProductSubscriptionPresenter @Inject constructor(
    private val webSocketWrapper: SocketWrapper
) : BasePresenter<ProductSubscriptionContract.View>(), ProductSubscriptionContract.Presenter {

    private var listener: ProductSubscriptionListener? = null

    override fun attachListener(listener: ProductSubscriptionListener) {
        this.listener = listener
    }

    override fun detachListener() {
        this.listener = null
    }

    override fun subscribe(productId: String) {
        if (webSocketWrapper.socketListener.isConnected) {
            val message = WebSocketMessage(subscriptions = arrayListOf(productId))
            webSocketWrapper.sendMessage(message.toJsonString())
            listener?.onProductSubscriptionChanged(productId, true)
        } else {
            Handler().postDelayed({
                subscribe(productId)
            }, 2000)
        }
    }

    override fun unsubscribe(productId: String) {
        if (webSocketWrapper.socketListener.isConnected) {
            val message = WebSocketMessage(unsubscriptions = arrayListOf(productId))
            webSocketWrapper.sendMessage(message.toJsonString())
            listener?.onProductSubscriptionChanged(productId, false)
        } else {
            Handler().postDelayed({
                unsubscribe(productId)
            }, 2000)
        }
    }
}
