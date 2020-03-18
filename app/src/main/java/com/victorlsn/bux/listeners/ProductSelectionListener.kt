package com.victorlsn.bux.listeners

import com.victorlsn.bux.data.api.models.Product
import com.victorlsn.bux.data.api.models.WebSocketMessage

interface ProductSelectionListener {
    fun onProductSelected(product: Product)
}