package fr.tahia910.android.positivityboost.network

import fr.tahia910.android.positivityboost.model.QuoteItem
import retrofit2.http.GET

interface QuoteApi {

    @GET("/")
    suspend fun getQuote(): QuoteItem
}