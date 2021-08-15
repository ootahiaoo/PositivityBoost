package com.example.android.positivityboost.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiClient {

    fun createRetrofitBuilder(): Retrofit.Builder {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val converterFactory = MoshiConverterFactory.create(moshi)
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(converterFactory)
    }
}