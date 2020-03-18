package com.victorlsn.bux.data.api.models

import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

class Product : BaseResponse() {
    @SerializedName("productMarketStatus")
    lateinit var marketStatus: MarketStatus

    lateinit var symbol: String
    lateinit var securityId: String
    lateinit var displayName: String
    lateinit var currentPrice: Price
    private lateinit var closingPrice: Price

    fun getFormattedPriceDiff() : String {
        val percentChange = (currentPrice.getAmount() - closingPrice.getAmount()) / closingPrice.getAmount()
        val formatter = DecimalFormat("##.##%")
        formatter.positivePrefix = "+ "
        formatter.negativePrefix = "- "
        return formatter.format(percentChange)
    }
}