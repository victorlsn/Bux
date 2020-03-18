package com.victorlsn.bux.data.api.websocket

import com.google.gson.Gson
import com.victorlsn.bux.data.api.models.WebSocketMessage
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SocketWrapper(private val client: OkHttpClient, private val request: Request, private val messageHandler: WebSocketMessageHandler) {
    private var socket: WebSocket? = null

    fun connectSocket() {
        socketListener.setMessageObserver(messageHandler)
        socket = client.newWebSocket(request, socketListener)
    }

    private fun reconnect() {
        Single.timer(2000, TimeUnit.MILLISECONDS)
            .subscribe({
                socket = client.newWebSocket(request, socketListener)
            }, {
                Timber.e(it)
            })
    }

    val socketListener = object : MyWebSocketListener() {
        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            reconnect()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            reconnect()
        }
    }

    fun sendMessage(message: String) {
        socket?.send(message)
    }
}