package com.victorlsn.bux.data.api.error_handling

import com.google.gson.Gson
import retrofit2.Response

class AuthenticationException(
	message: String?,
	url: String?,
	response: Response<*>?,
	kind: ErrorException.Kind,
	exception: Throwable
) : ErrorException(
	message,
	url,
	response,
	kind,
	exception
) {
	override var message: String? = null
	override var type: AuthenticationError.Type? = null

	init {
		try {
			var authenticationError: AuthenticationError?

			val errorJson = response?.errorBody()?.string()
			authenticationError = Gson().fromJson(
				errorJson,
				AuthenticationError::class.java
			)

			authenticationError?.let {
				this.message = authenticationError.message
				this.type = authenticationError.type
			}
		} catch (e: Exception) {
			this.message = message
			this.type = AuthenticationError.Type.UNKNOWN
		}
	}
}