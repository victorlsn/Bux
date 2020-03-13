package com.victorlsn.bux.data.api.services

import com.victorlsn.bux.data.api.models.Product
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/core/23/products/{productId}")
    fun getProductDetails(@Path("productId", encoded = true) orderId: String) : Observable<Product>
}