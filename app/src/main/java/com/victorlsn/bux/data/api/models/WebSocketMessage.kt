package com.victorlsn.bux.data.api.models

import com.google.gson.Gson
import java.io.Serializable

class WebSocketMessage(subscriptions: ArrayList<String>? = null, unsubscriptions: ArrayList<String>? = null) : Serializable {
    var subscribeTo : ArrayList<String>? = null
    var unsubscribeFrom : ArrayList<String>? = null
    var t : String? = null
    var body : WebSocketMessageBody? = null

    init {
        subscriptions?.let {
            subscribeTo = ArrayList()
            for (productId in subscriptions) {
                subscribeTo?.add("trading.product.$productId")
            }
        }

        unsubscriptions?.let {
            unsubscribeFrom = ArrayList()
            for (productId in unsubscriptions) {
                unsubscribeFrom?.add("trading.product.$productId")
            }
        }
    }

    fun toJsonString() : String {
        return Gson().toJson(this)
    }
}