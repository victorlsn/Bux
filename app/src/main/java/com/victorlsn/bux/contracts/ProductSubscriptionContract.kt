package com.victorlsn.bux.contracts

import com.victorlsn.bux.data.api.models.Product

class ProductSubscriptionContract {

    interface View :
        BaseView<Presenter>

    interface Presenter {
        fun subscribe(productId: String)
        fun unsubscribe(productId: String)
    }
}