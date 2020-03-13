package com.victorlsn.bux.data.api

import com.victorlsn.bux.data.api.error_handling.RxErrorHandlingWrapper
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class RxCallAdapterFactory : CallAdapter.Factory() {
	private var isErrorHandlingEnabled: Boolean = false

	private fun getCallAdapter(): CallAdapter.Factory {
		return RxJava2CallAdapterFactory.create()
	}

	override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
		if (!isErrorHandlingEnabled)
			return getCallAdapter().get(returnType, annotations, retrofit)

		return RxErrorHandlingWrapper(
			getCallAdapter().get(
				returnType,
				annotations,
				retrofit
			) ?: return null
		)
	}

	fun withErrorHandling(): RxCallAdapterFactory {
		this.isErrorHandlingEnabled = true

		return this
	}

	companion object {
		fun create(): CallAdapter.Factory {
			return RxCallAdapterFactory()
		}
	}
}