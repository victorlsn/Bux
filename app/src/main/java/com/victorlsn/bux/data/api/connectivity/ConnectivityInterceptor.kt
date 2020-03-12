package com.victorlsn.bux.data.api.connectivity

import android.app.Application
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(
	private val app: Application,
	private val connectivityInterface: ConnectivityChecker
) : Interceptor {

	override fun intercept(chain: Interceptor.Chain): Response {

		if (!connectivityInterface.isConnected()) {
			throw NoConnectivityException(app)
		}

		return chain.proceed(chain.request())
	}
}
