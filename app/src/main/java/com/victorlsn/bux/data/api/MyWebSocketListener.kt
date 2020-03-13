package com.victorlsn.bux.data.api

import io.reactivex.Observer
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import timber.log.Timber

class MyWebSocketListener : WebSocketListener() {

    private var observer : Observer<String>? = null

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Timber.d("WebSocket Open - %s", response.message)
        super.onOpen(webSocket, response)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Timber.d("WebSocket Closed - %s", reason)
        super.onClosed(webSocket, code, reason)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Timber.d("WebSocket Closing - %s", reason)
        super.onClosing(webSocket, code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Timber.d("WebSocket Failure - %s", response?.message)
        super.onFailure(webSocket, t, response)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Timber.d("WebSocket Message received - %s", bytes.hex())
        super.onMessage(webSocket, bytes)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Timber.d("WebSocket Message received - %s", text)
        observer?.onNext(text)
        super.onMessage(webSocket, text)
    }

    fun setMessageObserver(observer: Observer<String>) {
        this.observer = observer
    }
}