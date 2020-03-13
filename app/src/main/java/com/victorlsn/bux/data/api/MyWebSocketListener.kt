package com.victorlsn.bux.data.api

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import timber.log.Timber

class MyWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        Timber.d(response.message)
        super.onOpen(webSocket, response)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Timber.d(reason)
        super.onClosed(webSocket, code, reason)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Timber.d(reason)
        super.onClosing(webSocket, code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Timber.d(response?.message)
        super.onFailure(webSocket, t, response)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Timber.d(bytes.hex())
        super.onMessage(webSocket, bytes)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Timber.d(text)
        super.onMessage(webSocket, text)
    }
}