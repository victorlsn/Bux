package com.victorlsn.bux.di.modules

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.victorlsn.bux.BuildConfig
import com.victorlsn.bux.data.api.MyWebSocketListener
import com.victorlsn.bux.data.api.RxCallAdapterFactory
import com.victorlsn.bux.data.api.auth.TokenInterceptor
import com.victorlsn.bux.data.api.connectivity.ConnectivityInterceptor
import com.victorlsn.bux.data.api.connectivity.InternetConnectivityChecker
import com.victorlsn.bux.data.api.interceptors.HeaderInterceptor
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
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor,
        @Named("Language-Interceptor") languageInterceptor: HeaderInterceptor
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
            .addInterceptor(tokenInterceptor)
            .addInterceptor(languageInterceptor)
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
        return MyWebSocketListener()
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
            .addCallAdapterFactory(
                RxCallAdapterFactory()
                    .withErrorHandling()
            )
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
    fun provideApiService(retrofit: Retrofit): ApiService {
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

    @Singleton
    @Provides
    fun provideTokenInterceptor() : TokenInterceptor {
        return TokenInterceptor("Bearer eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6ImJiMGNkYTJiLWE" +
                "xMGUtNGVkMy1hZDVhLTBmODJiNGMxNTJjNCIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInN" +
                "jcCI6WyJhcHA6bG9naW4iLCJydGY6bG9naW4iXSwiZXhwIjoxODIwODQ5Mjc5LCJpYXQiOjE" +
                "1MDU0ODkyNzksImp0aSI6ImI3MzlmYjgwLTM1NzUtNGIwMS04NzUxLTMzZDFhNGRjOGY5MiI" +
                "sImNpZCI6Ijg0NzM2MjI5MzkifQ.M5oANIi2nBtSfIfhyUMqJnex-JYg6Sm92KPYaUL9GKg")
    }

    @Singleton
    @Provides
    @Named("Language-Interceptor")
    fun provideLanguageHeader() : HeaderInterceptor {
        return HeaderInterceptor("Accept-Language", "nl-NL,en;q=0.8")
    }
}