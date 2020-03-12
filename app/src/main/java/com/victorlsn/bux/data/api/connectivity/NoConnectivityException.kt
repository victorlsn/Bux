package com.victorlsn.bux.data.api.connectivity

import android.app.Application
import com.victorlsn.bux.R
import java.io.IOException

class NoConnectivityException(app: Application) : IOException() {
	override val message: String? = app.getString(R.string.check_connection)
}