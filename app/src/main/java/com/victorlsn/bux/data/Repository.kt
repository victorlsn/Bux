package com.victorlsn.bux.data

import com.victorlsn.bux.data.api.models.Product
import com.victorlsn.bux.data.api.services.ApiService
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {

    fun getProductDetails(productId: String) : Observable<Product> {
        return apiService.getProductDetails(productId)
            .flatMap {
                Observable.just(it)
            }
    }
}