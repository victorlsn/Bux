package com.victorlsn.bux.listeners

import com.victorlsn.bux.data.api.models.WebSocketMessage

interface MessageListener {
    fun onMessageReceived(message: WebSocketMessage)
}