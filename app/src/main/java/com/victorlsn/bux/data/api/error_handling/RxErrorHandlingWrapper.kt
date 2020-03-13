package com.victorlsn.bux.data.api.error_handling

import com.victorlsn.bux.data.api.error_handling.ErrorException.Companion.asRetrofitException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class RxErrorHandlingWrapper<R>(private val wrapped: CallAdapter<R, *>) : CallAdapter<R, Any> {

	override fun responseType(): Type {
		return wrapped.responseType()
	}

	override fun adapt(call: Call<R>): Any {
		return when (val result = wrapped.adapt(call)) {
			is Single<*> -> result.onErrorResumeNext(Function { throwable -> Single.error(asRetrofitException(throwable)) })
			is Observable<*> -> result.onErrorResumeNext(Function { throwable ->
				Observable.error(
					asRetrofitException(
						throwable
					)
				)
			})
			is Completable -> result.onErrorResumeNext(Function { throwable ->
				Completable.error(
					asRetrofitException(
						throwable
					)
				)
			})
			else -> result
		}
	}
}
