package com.apolis.moviesapp_retroft3.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private lateinit var myRetrofit: Retrofit

    private fun getRetrofit(): Retrofit{

        if(!this::myRetrofit.isInitialized) {
            val apiKeyInterceptor = ApiKeyInterceptor()
            val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val retryInterceptor = RetryInterceptor(3)
            val client = OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(retryInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()


            myRetrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }

        return myRetrofit
    }

    val apiService: ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }
}