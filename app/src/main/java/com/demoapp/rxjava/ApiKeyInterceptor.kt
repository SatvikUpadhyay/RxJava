package com.apolis.moviesapp_retroft3.api

import com.apolis.moviesapp_retroft3.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val currentUrl = chain.request().url
        val newUrl = currentUrl.newBuilder().addQueryParameter("api_key", Constants.API_KEY).build()

        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.url(newUrl).build()

        val response = chain.proceed(newRequest)

        return response
    }
}