package com.victorlsn.bux.contracts

import com.victorlsn.bux.data.api.models.Product

class ProductsContract {

    interface View :
        BaseView<Presenter> {
        fun onProductDetailsSuccess(product: Product)

        fun onProductDetailsFailure(error: String?)

        fun showLoading()

        fun hideLoading()
    }

    interface Presenter {
        fun requestAllProductsDetails()
        fun requestProductDetails(productId: String)
        fun subscribe(productId: String)
    }
}