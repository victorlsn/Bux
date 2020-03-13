package com.victorlsn.bux.data.api.error_handling

import com.google.gson.annotations.SerializedName

class AuthenticationError {

	@SerializedName("error")
	var type: Type? = null

	@SerializedName("error_description")
	var message: String? = null

	enum class Type {
		@SerializedName("unsupported_grant_type")
		UNSUPPORTED_GRANT_TYPE,

		@SerializedName("invalid_client")
		INVALID_CLIENT,

		UNKNOWN
	}
}
