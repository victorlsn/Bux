package com.victorlsn.bux.listeners

interface ProductSubscriptionListener {
    fun onProductSubscriptionChanged(securityId: String, subscribed: Boolean)
}