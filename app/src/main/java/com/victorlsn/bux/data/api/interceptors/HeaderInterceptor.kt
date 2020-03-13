package com.victorlsn.bux.data.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

open class HeaderInterceptor constructor(private val header: String, private val value: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val requestBuilder = original.newBuilder()
            .addHeader(header,value)
            .url(originalHttpUrl)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}