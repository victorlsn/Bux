package com.victorlsn.bux.data.api.models

import com.google.gson.Gson
import java.io.Serializable

class WebSocketResponseMessage : Serializable {
    var t : String = ""

    fun toJsonString() : String {
        return Gson().toJson(this)
    }
}