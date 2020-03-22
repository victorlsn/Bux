package com.victorlsn.bux.data.api.models

import java.io.Serializable
import java.text.NumberFormat
import java.util.*

class Price : Serializable {
    var currency: String? = null
    var decimals: Int? = null
    var amount: String? = null

    fun getAmount(): Float? {
        return amount?.toFloat()
    }

    fun getFormattedPrice(): String? {
        if (currency != null && decimals != null && amount != null) {
            val currency = Currency.getInstance(currency)
            val numberFormat = NumberFormat.getCurrencyInstance()

            numberFormat.maximumFractionDigits = decimals!!
            numberFormat.currency = currency

            return numberFormat.format(amount?.toFloat())
        }
        return null
    }
}