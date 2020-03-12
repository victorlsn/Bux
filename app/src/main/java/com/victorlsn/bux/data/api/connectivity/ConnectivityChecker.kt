package com.victorlsn.bux.data.api.connectivity

interface ConnectivityChecker {
    fun isConnected(): Boolean
}