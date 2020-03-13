package com.victorlsn.bux.data.api.auth

import com.victorlsn.bux.data.api.interceptors.HeaderInterceptor

class TokenInterceptor constructor(token: String) : HeaderInterceptor("Authorization", token)