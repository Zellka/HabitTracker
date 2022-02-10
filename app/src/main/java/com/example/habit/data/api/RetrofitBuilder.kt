package com.example.habit.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"

    private val headerInterceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "token")
            .build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(headerInterceptor)

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}

