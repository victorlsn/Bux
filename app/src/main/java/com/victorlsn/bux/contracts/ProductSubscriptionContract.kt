package com.victorlsn.bux.contracts

import com.victorlsn.bux.listeners.ProductSubscriptionListener

class ProductSubscriptionContract {

    interface View :
        BaseView<Presenter>

    interface Presenter {
        fun attachListener(listener: ProductSubscriptionListener)
        fun detachListener()
        fun subscribe(productId: String)
        fun unsubscribe(productId: String)
    }
}