package com.victorlsn.bux.data.api.websocket

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.victorlsn.bux.data.api.models.WebSocketMessage
import com.victorlsn.bux.listeners.MessageListener
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class WebSocketMessageHandler : Observer<String> {
    var webSocketListener: MyWebSocketListener? = null

    private var listener: MessageListener? = null
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    val handler = Handler(Looper.getMainLooper())

    override fun onNext(t: String) {
        val message = Gson().fromJson(t, WebSocketMessage::class.java)
        when (message.t) {
            "connect.connected" -> {
                webSocketListener?.isConnected = true
            }
            "trading.quote" -> {
                val runnable = Runnable {
                    listener?.onMessageReceived(message)
                }
                handler.post(runnable)
            }
        }
    }

    override fun onError(e: Throwable) {
        val runnable = Runnable {
            listener?.onError()
        }
        handler.post(runnable)
    }

    fun setListener(listener: MessageListener) {
        this.listener = listener
    }
}