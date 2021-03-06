package com.victorlsn.bux.data.api.models

import com.victorlsn.bux.util.getLocalFromISO
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
            val numberFormat = NumberFormat.getCurrencyInstance(getLocalFromISO(currency!!))
            numberFormat.maximumFractionDigits = decimals!!

            return numberFormat.format(amount?.toFloat())
        }
        return null
    }
}