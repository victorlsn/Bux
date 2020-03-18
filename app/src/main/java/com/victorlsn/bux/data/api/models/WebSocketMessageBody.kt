package com.victorlsn.bux.data.api.models

import com.google.gson.Gson
import java.io.Serializable

class WebSocketMessageBody : Serializable {
    var securityId : String? = null
    var currentPrice: String? = null
}