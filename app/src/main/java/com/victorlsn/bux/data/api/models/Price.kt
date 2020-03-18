package com.victorlsn.bux.data.api.models

import java.text.NumberFormat
import java.util.*

class Price {
    var currency: String? = null
    var decimals: Int? = null
    var amount: String? = null

    fun getAmount() : Float {
        return amount!!.toFloat()
    }

    fun getFormattedPrice() : String {
        val currency = Currency.getInstance(currency)
        val numberFormat = NumberFormat.getCurrencyInstance()

        numberFormat.maximumFractionDigits = decimals!!
        numberFormat.currency = currency

        return numberFormat.format(amount?.toFloat())
    }
}