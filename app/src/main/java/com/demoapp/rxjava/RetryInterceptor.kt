package com.apolis.moviesapp_retroft3.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.RuntimeException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

class RetryInterceptor(val retryAttempts: Int): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        for(i in 1..retryAttempts) {
            try {
                val response = chain.proceed(chain.request())
                return response
            } catch (ste: SocketTimeoutException) {
                ste.printStackTrace()
                Log.d("RetryInterceptor", "Request Timeout. Resend attempt : $i")
            }
        }

        throw RuntimeException("Failed to complete request")
    }
}