package com.victorlsn.bux.data.api.models

class Product : BaseResponse() {
    var symbol: String? = null
    var securityId: String? = null
    var displayName: String? = null
    var currentPrice: Price? = null
    var closingPrice: Price? = null
}