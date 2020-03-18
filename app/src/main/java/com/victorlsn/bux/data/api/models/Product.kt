package com.victorlsn.bux.data.api.models

import java.text.NumberFormat

class Product : BaseResponse() {
    var symbol: String? = null
    var securityId: String? = null
    var displayName: String? = null
    var currentPrice: Price? = null
    var closingPrice: Price? = null

    fun getFormattedPriceDiff() : String {
        return NumberFormat.getPercentInstance().format(currentPrice!!.getAmount() - closingPrice!!.getAmount() / closingPrice!!.getAmount() * 100f)
    }
}