package fr.tahia910.android.positivityboost.repository

import fr.tahia910.android.positivityboost.network.QuoteApi
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class QuoteRepository(private val quoteApi: QuoteApi) : KoinComponent {

    fun getFlowQuote() = flow { emit(quoteApi.getQuote()) }
}