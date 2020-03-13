package com.victorlsn.bux.data.api

import com.google.gson.Gson
import com.victorlsn.bux.data.api.models.WebSocketMessage
import com.victorlsn.bux.listeners.MessageListener
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class WebSocketMessageHandler : Observer<String> {
    private var listener: MessageListener? = null
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: String) {
        val webSocketResponseMessage = Gson().fromJson(t, WebSocketMessage::class.java)
        listener?.onMessageReceived(webSocketResponseMessage)
    }

    override fun onError(e: Throwable) {

    }

    fun setListener(listener: MessageListener) {
        this.listener = listener
    }
}