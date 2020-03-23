package com.victorlsn.bux.data.api.models

import com.victorlsn.bux.util.getLocalFromISO
import java.io.Serializable
import java.text.NumberFormat

class Range : Serializable {
    var currency: String? = null
    var decimals: Int? = null
    var low: String? = null
    var high: String? = null

    fun getFormattedRange(): String? {
        if (currency != null && decimals != null && low != null && high != null) {
            val numberFormat = NumberFormat.getCurrencyInstance(getLocalFromISO(currency!!))
            numberFormat.maximumFractionDigits = decimals!!

            val min = numberFormat.format(low?.toFloat())
            val max = numberFormat.format(high?.toFloat())

            return "$min - $max"
        }
        return null
    }
}