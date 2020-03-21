package com.victorlsn.bux.listeners

import com.victorlsn.bux.data.api.models.Product

interface ProductSelectionListener {
    fun onProductSelected(product: Product)
}