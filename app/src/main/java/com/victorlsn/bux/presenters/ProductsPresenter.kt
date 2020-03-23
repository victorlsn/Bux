package com.victorlsn.bux.presenters

import android.os.Handler
import com.victorlsn.bux.contracts.ProductsContract
import com.victorlsn.bux.data.Repository
import com.victorlsn.bux.data.api.error_handling.ErrorException
import com.victorlsn.bux.data.api.models.Product
import com.victorlsn.bux.data.api.models.WebSocketMessage
import com.victorlsn.bux.data.api.websocket.SocketWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class ProductsPresenter @Inject constructor(
    private val repository: Repository,
    private val webSocketWrapper: SocketWrapper
) : BasePresenter<ProductsContract.View>(), ProductsContract.Presenter {

    private val products = mapOf(
        Pair("Apple", "sb26513"),
        Pair("Deutsche Bank", "sb28248"),
        Pair("EUR/USD", "sb26502"),
        Pair("Germany30", "sb26493"),
        Pair("Gold", "sb26500"),
        Pair("US500", "sb26496")
    )

    override fun requestAllProductsDetails() {
        view?.showLoading()

        for (product in products) {
            requestProductDetails(product.value)
        }
    }

    override fun requestProductDetails(productId: String) {
        Timber.d("Requesting details for product %s", productId)

        disposable.add(
            repository.getProductDetails(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getProductDetailsSuccessfully, this::getProductDetailsFailure)
        )
    }

    private fun getProductDetailsSuccessfully(product: Product) {
        Timber.d("Product retrieved successfully")

        if (product.errorCode != null) {
            Timber.e(product.developerMessage)
            getProductDetailsFailure(Error(product.message))
        } else {
            view?.hideLoading()
            view?.onProductDetailsSuccess(product)
        }
    }

    private fun getProductDetailsFailure(error: Throwable) {
        Timber.e(error)
        view?.hideLoading()
        try {
            val exception = error as ErrorException
            val cause = exception.cause as HttpException
            val jsonObject = JSONObject(cause.response()!!.errorBody()!!.string())

            view?.onProductDetailsFailure(jsonObject.getString("message"))
        } catch (e: Throwable) {
            view?.onProductDetailsFailure(null)
        }
        Handler().postDelayed({
            requestAllProductsDetails()
        }, 5000)
    }


    override fun subscribe(productId: String) {
        if (webSocketWrapper.socketListener.isConnected) {
            val message = WebSocketMessage(subscriptions = arrayListOf(productId))
            webSocketWrapper.sendMessage(message.toJsonString())
        } else {
            Handler().postDelayed({
                subscribe(productId)
            }, 2000)
        }
    }
}
