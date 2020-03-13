package com.victorlsn.bux.presenters

import com.victorlsn.bux.contracts.ProductsContract
import com.victorlsn.bux.data.Repository
import com.victorlsn.bux.data.api.error_handling.ErrorException
import com.victorlsn.bux.data.api.models.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class ProductsPresenter @Inject constructor(
        private val repository: Repository
    ) : BasePresenter<ProductsContract.View>(), ProductsContract.Presenter {

    override fun requestProductDetails(productId: String) {
        Timber.d("Requesting details for product %s", productId)
        view?.showLoading()

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
        }
        else {
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

            view?.onProductDetailsFailure(jsonObject.getString("error_description"))
        } catch (e: Throwable) {
            view?.onProductDetailsFailure(error.localizedMessage!!)
        }
    }

}
