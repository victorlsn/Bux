package com.victorlsn.bux.data.api.connectivity

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class InternetConnectivityChecker(
	val app: Application
) : ConnectivityChecker {

	override fun isConnected(): Boolean {
		return (app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)
			?.activeNetworkInfo?.isConnected == true
	}
}