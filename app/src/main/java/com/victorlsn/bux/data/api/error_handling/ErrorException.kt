package com.victorlsn.bux.data.api.error_handling

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

open class ErrorException(
	message: String?,
	open val url: String?,
	open val response: Response<*>?,
	open val kind: Kind,
	exception: Throwable,
	open val type: Any? = null
) : RuntimeException(message, exception) {

	override fun toString(): String {
		return super.toString() + " : " + kind + " : " + url + " : " + response?.errorBody()?.string()
	}

	enum class Kind {
		NETWORK,
		HTTP,
		UNEXPECTED
	}

	companion object {
		private fun httpError(
			url: String,
			response: Response<*>,
			httpException: HttpException
		): ErrorException {
			val message = response.code().toString() + " " + response.message()

			if (response.code() == 401 || response.code() == 400) {

				return AuthenticationException(
					message,
					url,
					response,
					Kind.HTTP,
					httpException
				)
			}

			return ErrorException(
				message,
				url,
				response,
				Kind.HTTP,
				httpException
			)
		}

		private fun networkError(exception: IOException): ErrorException {
			return ErrorException(
				exception.message,
				null,
				null,
				Kind.NETWORK,
				exception
			)
		}

		private fun unexpectedError(exception: Throwable): ErrorException {
			return ErrorException(
				exception.message,
				null,
				null,
				Kind.UNEXPECTED,
				exception
			)
		}

		fun asRetrofitException(throwable: Throwable): ErrorException {
			if (throwable is ErrorException) {
				return throwable
			}
			// We had non-200 http error
			if (throwable is HttpException) {
				val response = throwable.response()
				return httpError(
					response?.raw()?.request?.url.toString(),
					response!!,
					throwable
				)
			}
			// A network error happened
			return if (throwable is IOException) {
				ErrorException.Companion.networkError(
					throwable
				)
			} else ErrorException.Companion.unexpectedError(
				throwable
			)
			// We don't know what happened. We need to simply convert to an unknown error
		}
	}
}