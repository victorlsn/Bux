package com.victorlsn.bux.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.victorlsn.bux.R
import com.victorlsn.bux.contracts.ProductsContract
import com.victorlsn.bux.data.api.models.Product
import com.victorlsn.bux.presenters.ProductsPresenter
import javax.inject.Inject

class MainActivity : BaseActivity(), ProductsContract.View {

    @Inject
    lateinit var presenter: ProductsPresenter

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.requestProductDetails("sb26496")
    }

    override fun onProductDetailsSuccess(product: Product) {

    }

    override fun onProductDetailsFailure(error: String?) {
        val errorMessage = error ?: getString(R.string.default_error_message)
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        loading.show()
    }

    override fun hideLoading() {
        loading.dismiss()
    }

}