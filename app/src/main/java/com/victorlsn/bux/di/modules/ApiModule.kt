package com.victorlsn.bux.di.modules

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.victorlsn.bux.BuildConfig
import com.victorlsn.bux.data.api.connectivity.ConnectivityInterceptor
import com.victorlsn.bux.data.api.connectivity.InternetConnectivityChecker
import com.victorlsn.bux.data.api.services.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    val REST_URL = "https://api.beta.getbux.com/"
    val WEBSOCKET_URL = "https://rtf.beta.getbux.com/subscriptions/me"
    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(
        app: Application,
        connectivityChecker: InternetConnectivityChecker,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {

        httpLoggingInterceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.HEADERS

        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(
                ConnectivityInterceptor(
                    app,
                    connectivityChecker
                )
            )
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideWebSocket(
        client: OkHttpClient,
        request: Request,
        webSocketListener: WebSocketListener
    ): WebSocket {
        return client.newWebSocket(request, webSocketListener)
    }

    @Singleton
    @Provides
    fun provideWebSocketRequest(
    ): Request {
        return Request.Builder()
            .url(WEBSOCKET_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideWebSocketListener(
    ): WebSocketListener {
        return object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Timber.d(response.message)
                super.onOpen(webSocket, response)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Timber.d(reason)
                super.onClosed(webSocket, code, reason)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Timber.d(reason)
                super.onClosing(webSocket, code, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Timber.d(response?.message)
                super.onFailure(webSocket, t, response)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Timber.d(bytes.hex())
                super.onMessage(webSocket, bytes)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Timber.d(text)
                super.onMessage(webSocket, text)
            }
        }
    }


    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(REST_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder
    ): Retrofit {
        return retrofitBuilder.build()
    }

    @Singleton
    @Provides
    fun provideApiService(@Named("Retrofit") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.HEADERS

        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideConnectivityChecker(app: Application): InternetConnectivityChecker {
        return InternetConnectivityChecker(
            app
        )
    }
}