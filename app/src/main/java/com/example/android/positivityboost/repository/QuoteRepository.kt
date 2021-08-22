package com.example.android.positivityboost.repository

import com.example.android.positivityboost.network.QuoteApi
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class QuoteRepository(private val quoteApi: QuoteApi) : KoinComponent {

    fun getFlowQuote() = flow { emit(quoteApi.getQuote().affirmation) }
}