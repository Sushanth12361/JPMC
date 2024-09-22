package com.example.jpmorgantest.data.settings.network

import com.example.jpmorgantest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class WeatherInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain
            .request()
            .url
            .newBuilder()
            .addQueryParameter(TOKEN_KEY, BuildConfig.WEATHER_KEY)
            .build()

        return chain.proceed(chain.request().newBuilder().url(url).build())
    }
    companion object {
        const val TOKEN_KEY = "appid"
    }
}
