package com.example.android.positivityboost.network

import com.example.android.positivityboost.model.QuoteItem
import retrofit2.http.GET

interface QuoteApi {

    @GET("/")
    suspend fun getQuote(): QuoteItem
}